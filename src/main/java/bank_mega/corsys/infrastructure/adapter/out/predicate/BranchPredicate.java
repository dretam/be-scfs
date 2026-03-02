package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.branch.BranchId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class BranchPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<BranchJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("idBranch")), like),
                            cb.like(cb.lower(root.get("branchName")), like),
                            cb.like(cb.lower(root.get("category")), like),
                            cb.like(cb.lower(root.get("regional")), like),
                            cb.like(cb.lower(root.get("area")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<BranchJpaEntity> root, BranchId id) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.equal(root.get("id"), id.value())
        );

        return predicates.toArray(Predicate[]::new);
    }

}
