package ru.senla.realestatemarket.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAbstractService<M, I> {


    M getOne(Specification<M> specification);

    M getById(I id);

    List<M> getAll();
    List<M> getAll(Specification<M> specification);
    List<M> getAll(Sort sort);
    List<M> getAll(Specification<M> specification, Sort sort);
    List<M> getAll(Specification<M> specification, String sortQuery);
    List<M> getAll(String rsqlQuery, Sort sort);
    List<M> getAll(Specification<M> specification, String rsqlQuery, Sort sort);
    List<M> getAll(String rsqlQuery, String sortQuery);
    List<M> getAll(Specification<M> specification, String rsqlQuery, String sortQuery);

    void add(M model);

    void update(M model);
    void updateById(M model, I id);

    void deleteById(I id);

}
