package ru.senla.realestatemarket.repo;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.senla.realestatemarket.exception.WrongRSQLQueryException;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
public abstract class AbstractRepositoryImpl<M, I> implements IAbstractRepository<M, I> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;
    protected Class<M> clazz;


    @PostConstruct
    public void initAbstract() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public abstract void init();


    public void setClazz(final Class<M> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet, null);
    }

    @Override
    public M findOne(@NonNull Specification<M> specification) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        criteriaQuery
                .select(root);

        Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        criteriaQuery
                .where(predicate);

        try {
            return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public M findById(@NonNull I id) {
        return entityManager.find(clazz, id);
    }

    private <T> List<M> findAll(@NonNull CriteriaQuery<M> criteriaQuery,
                                @NonNull From<T, M> from,
                                @Nullable List<Predicate> predicateList,
                                @Nullable List<Order> orderList
    ) {
        criteriaQuery
                .select(from);

        if (predicateList != null) {
            criteriaQuery
                    .where(predicateList.toArray(new Predicate[0]));
        }

        if (orderList != null) {
            criteriaQuery
                    .orderBy(orderList);
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<M> findAllByPredicateAndOrderList(
            @Nullable List<Predicate> predicateList, @Nullable List<Order> orderList
    ) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        return findAll(criteriaQuery, root, predicateList, orderList);
    }

    @Override
    public List<M> findAll(@Nullable Specification<M> specification, @Nullable Sort sort) {
        return findAllByQuery(specification, null, sort);
    }

    @Override
    public List<M> findAll() {
        return findAll(null, null);
    }

    @Override
    public List<M> findAll(Specification<M> specification) {
        return findAll(specification, null);
    }

    @Override
    public List<M> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<M> findAllByQuery(@Nullable String rsqlQuery, @Nullable Sort sort) {
        return findAllByQuery(null, rsqlQuery, sort);
    }

    @Override
    public List<M> findAllByQuery(@Nullable Specification<M> specification,
                                  @Nullable String rsqlQuery,
                                  @Nullable Sort sort
    ) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        List<Predicate> predicateList = new LinkedList<>();
        if (rsqlQuery != null) {
            RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<M>().defineRoot(root);

            Node rootNode = null;
            try {
                rootNode = new RSQLParser().parse(rsqlQuery);
            } catch (Exception e) {
                String message = "Failed to parse the given RSQL query";

                log.error(message);
                throw new WrongRSQLQueryException(message);
            }

            predicateList.add(rootNode.accept(visitor, entityManager));
        }

        if (specification != null) {
            predicateList.add(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }

        List<Order> orderList = null;
        if (sort != null) {
            orderList = QueryUtils.toOrders(sort, root, criteriaBuilder);
        }

        return findAll(criteriaQuery, root, predicateList, orderList);
    }

    @Override
    public void delete(M model) {
        entityManager.remove(model);
    }

    @Override
    public void deleteById(I id) {
        M model = findById(id);
        delete(model);
    }

    @Override
    public void create(M newModel) {
        entityManager.persist(newModel);
    }

    @Override
    public void update(M changedModel) {
        entityManager.merge(changedModel);
    }

    @Override
    public boolean isExist(M model) {
        return entityManager.contains(model);
    }

    @Override
    public boolean isExist(Specification<M> specification) {
        return !findAll(specification).isEmpty();
    }

    @Override
    public Integer size() {
        return findAll().size();
    }

}
