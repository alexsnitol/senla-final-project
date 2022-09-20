package ru.senla.realestatemarket.repo.announcement.impl;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.announcement.IAbstractAnnouncementRepository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractAnnouncementRepositoryImpl<M>
        extends AbstractRepositoryImpl<M, Long>
        implements IAbstractAnnouncementRepository<M> {


    protected List<M> findAllBySpecificationAndLikePredicates(
            Specification<M> specification, List<Predicate> likePredicates,
            CriteriaQuery<M> criteriaQuery, Root<M> root
    ) {
        criteriaQuery
                .select(root)
                .distinct(true);

        if (specification != null) {
            Predicate requirementPredicate
                    = specification.toPredicate(root, criteriaQuery, criteriaBuilder);

            criteriaQuery
                    .where(
                            criteriaBuilder.and(
                                    requirementPredicate,
                                    criteriaBuilder.or(likePredicates.toArray(new Predicate[0]))
                            )
                    );
        } else {
            criteriaQuery
                    .where(criteriaBuilder.or(likePredicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
