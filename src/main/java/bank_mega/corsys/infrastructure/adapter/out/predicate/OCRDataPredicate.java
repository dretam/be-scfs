package bank_mega.corsys.infrastructure.adapter.out.predicate;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.OCRDataJpaEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class OCRDataPredicate {

    public static Predicate[] listBuild(CriteriaBuilder cb, Root<OCRDataJpaEntity> root, String filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );

        if (filter != null && !filter.isBlank()) {
            String like = "%" + filter.toLowerCase() + "%";

            predicates.add(
                    cb.or(
                            cb.like(cb.lower(root.get("atasNama")), like),
                            cb.like(cb.lower(root.get("nominal")), like),
                            cb.like(cb.lower(root.get("periode")), like),
                            cb.like(cb.lower(root.get("alokasi")), like)
                    )
            );
        }

        return predicates.toArray(Predicate[]::new);
    }

    public static Predicate[] retrieveBuild(CriteriaBuilder cb, Root<OCRDataJpaEntity> root, Long id) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.isNull(root.get("audit").get("deletedAt"))
        );
        predicates.add(
                cb.equal(root.get("id"), id)
        );

        return predicates.toArray(Predicate[]::new);
    }

}
