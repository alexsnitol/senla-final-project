package ru.senla.realestatemarket.repo;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import com.sun.istack.NotNull;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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


    protected abstract <T> void fetchSelection(From<T, M> from);

    @Override
    public M findOne(@NotNull Specification<M> specification) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        fetchSelection(root);

        criteriaQuery
                .select(root);

        Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        criteriaQuery
                .where(predicate);

        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
    }

    @Override
    public M findById(I id) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        fetchSelection(root);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("id"), id));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private <T> List<M> findAll(@NotNull CriteriaQuery<M> criteriaQuery,
                                @NotNull From<T, M> from,
                                @Nullable Predicate predicate,
                                @Nullable List<Order> orderList) {
        fetchSelection(from);

        criteriaQuery
                .select(from);

        if (predicate != null) {
            criteriaQuery
                    .where(predicate);
        }

        if (orderList != null) {
            criteriaQuery
                    .orderBy(orderList);
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<M> findAllByPredicateAndOrderList(@Nullable Predicate predicate, @Nullable List<Order> orderList) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        return findAll(criteriaQuery, root, predicate, orderList);
    }

    public List<M> findAll(@Nullable Specification<M> specification, @Nullable Sort sort) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<M> root = criteriaQuery.from(clazz);

        Predicate predicate = null;
        if (specification != null) {
            predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        }

        List<Order> orderList = null;
        if (sort != null) {
            orderList = QueryUtils.toOrders(sort, root, criteriaBuilder);
        }

        return findAll(criteriaQuery, root, predicate, orderList);
    }

    @Override
    public List<M> findAll() {
        return findAll(null, null);
    }

    public List<M> findAll(Specification<M> specification) {
        return findAll(specification, null);
    }

    public List<M> findAll(Sort sort) {
        return findAll(null, sort);
    }

    public List<M> findAllByQuery(@Nullable String rsqlQuery, @Nullable Sort sort) {
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(clazz);
        From<M, M> root = criteriaQuery.from(clazz);

        Predicate predicate = null;
        if (rsqlQuery != null) {
            RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<M>().defineRoot(root);

            Node rootNode = new RSQLParser().parse(rsqlQuery);

            predicate = rootNode.accept(visitor, entityManager);
        }

        List<Order> orderList = null;
        if (sort != null) {
            orderList = QueryUtils.toOrders(sort, root, criteriaBuilder);
        }

        return findAll(criteriaQuery, root, predicate, orderList);
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
    public Integer size() {
        return findAll().size();
    }

}
