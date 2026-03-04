package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class PermissionPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<PermissionJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("name")), like),
                            cb.like(cb.lower(root.get("code")), like),
                            cb.like(cb.lower(root.get("description")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<PermissionJpaEntity> root, PermissionId id) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );
        predicates.add(
                cb.equal(root.get("id"), id.value())
        );

        return predicates.toArray(Predicate[]::new);
    }

}
