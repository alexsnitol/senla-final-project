package ru.senla.realestatemarket.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IAbstractRepository<M, I> {


    M findOne(Specification<M> specification);
    M findById(I id);
    List<M> findAll();
    List<M> findAll(Specification<M> specification);
    List<M> findAll(Sort sort);
    List<M> findAll(@Nullable Specification<M> specification, @Nullable Sort sort);
    List<M> findAllByQuery(String rsqlQuery, Sort sort);
    List<M> findAllByQuery(Specification<M> specification, String rsqlQuery, Sort sort);

    void delete(M model);
    void deleteById(I id);

    void create(M newModel);

    void update(M changedModel);

    boolean isExist(M model);

    boolean isExist(Specification<M> specification);

    Integer size();

}
