package bank_mega.corsys.infrastructure.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParserUtil {

    public static Set<String> expandParse(String expand) {
        if (expand == null || expand.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(expand.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    public static Sort sortParse(String[] allowed, String sort, String defaultField) {
        if (sort == null || sort.isEmpty()) {
            return Sort.by(defaultField).ascending();
        }

        List<Sort.Order> orders = new ArrayList<>();

        for (String s : sort.split(",")) {
            s = s.trim();
            boolean desc = s.startsWith("-");
            String field = desc ? s.substring(1) : s;

            // validasi field
            if (!Arrays.asList(allowed).contains(field)) continue;

            orders.add(desc ? Sort.Order.desc(field) : Sort.Order.asc(field));
        }

        return orders.isEmpty()
                ? Sort.by(defaultField).ascending()
                : Sort.by(orders);
    }

    public static List<Order> toOrders(Sort sort, CriteriaBuilder cb, Root<?> root) {
        if (sort == null) return List.of();

        return sort.stream()
                .map(order -> {
                    Path<?> path = resolvePath(root, order.getProperty());
                    return order.isAscending()
                            ? cb.asc(path)
                            : cb.desc(path);
                })
                .toList();
    }

    private static Path<?> resolvePath(Root<?> root, String property) {
        if (!property.contains(".")) {
            return root.get(property);
        }

        String[] parts = property.split("\\.");
        Path<?> path = root;

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

}
