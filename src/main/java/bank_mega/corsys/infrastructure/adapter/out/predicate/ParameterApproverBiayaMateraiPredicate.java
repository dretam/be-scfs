package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ParameterApproverBiayaMateraiJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ParameterApproverBiayaMateraiPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<ParameterApproverBiayaMateraiJpaEntity> root, String filter) {
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

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<ParameterApproverBiayaMateraiJpaEntity> root, ParameterApproverBiayaMateraiId id) {
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
