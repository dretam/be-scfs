package bank_mega.corsys.infrastructure.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.function.BiFunction;

public class PersistenceUtil {

    public static <T> long count(
            EntityManager em,
            CriteriaBuilder cb,
            Class<T> entityClass,
            BiFunction<CriteriaBuilder, Root<T>, List<Predicate>> predicateBuilder
    ) {

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);

        // WHERE
        List<Predicate> predicates = predicateBuilder.apply(cb, root);
        cq.where(predicates.toArray(Predicate[]::new));

        // SELECT COUNT(root)
        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }

}
