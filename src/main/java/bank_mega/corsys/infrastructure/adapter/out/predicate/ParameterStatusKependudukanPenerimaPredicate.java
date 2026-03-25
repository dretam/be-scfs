package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterStatusKependudukanPenerimaJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ParameterStatusKependudukanPenerimaPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<ParameterStatusKependudukanPenerimaJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.like(cb.lower(root.get("value")), like)
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<ParameterStatusKependudukanPenerimaJpaEntity> root, ParameterStatusKependudukanPenerimaId id) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );
        predicates.add(
                cb.equal(root.get("code"), id.value())
        );

        return predicates.toArray(Predicate[]::new);
    }

}
