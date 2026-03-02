package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.InternalUserJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class InternalUserPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<InternalUserJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("userName")), like),
                            cb.like(cb.lower(root.get("nama")), like),
                            cb.like(cb.lower(root.get("email")), like),
                            cb.like(cb.lower(root.get("jobTitle")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<InternalUserJpaEntity> root, InternalUserName userName) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.equal(root.get("userName"), userName.value())
        );

        return predicates.toArray(Predicate[]::new);
    }

}
