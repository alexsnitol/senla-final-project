package ru.senla.realestatemarket.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IAbstractService<M, I> {


    M getOne(Specification<M> specification);

    M getById(I id);

    List<M> getAll();
    List<M> getAll(Specification<M> specification);
    List<M> getAll(Sort sort);
    List<M> getAll(@Nullable Specification<M> specification, @Nullable Sort sort);
    List<M> getAll(@Nullable Specification<M> specification, @Nullable String sortQuery);
    List<M> getAll(@Nullable String rsqlQuery, @Nullable Sort sort);
    List<M> getAll(@Nullable String rsqlQuery, @Nullable String sortQuery);

    void add(M model);

}
