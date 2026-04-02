package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.activitylog.ActivityLogId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ActivityLogJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<ActivityLogJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("user").get("name")), like),
                            cb.like(cb.lower(root.get("user").get("email")), like),
                            cb.like(cb.lower(root.get("ipAddress")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<ActivityLogJpaEntity> root, ActivityLogId id) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.equal(root.get("id"), id.value())
        );

        return predicates.toArray(Predicate[]::new);
    }

}
