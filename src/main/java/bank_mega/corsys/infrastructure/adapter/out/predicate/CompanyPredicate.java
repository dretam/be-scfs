package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.company.CompanyType;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CompanyJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class CompanyPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<CompanyJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("cif")), like),
                            cb.like(cb.lower(root.get("name")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<CompanyJpaEntity> root, CompanyId id) {
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
