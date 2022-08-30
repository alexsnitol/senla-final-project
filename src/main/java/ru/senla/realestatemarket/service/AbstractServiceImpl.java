package ru.senla.realestatemarket.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.senla.realestatemarket.repo.IAbstractRepository;
import ru.senla.realestatemarket.util.SortUtil;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
public abstract class AbstractServiceImpl <M, I> implements IAbstractService<M, I> {

    @Setter
    protected IAbstractRepository<M, I> defaultRepository;
    protected Class<M> clazz;


    public abstract void init();

    public void setClazz(final Class<M> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet, null);
    }


    @Override
    public M getOne(Specification<M> specification) {
        return defaultRepository.findOne(specification);
    }

    @Override
    public M getById(I id) {
        return defaultRepository.findById(id);
    }

    @Override
    public List<M> getAll() {
        return defaultRepository.findAll();
    }

    @Override
    public List<M> getAll(Specification<M> specification) {
        return defaultRepository.findAll(specification);
    }

    @Override
    public List<M> getAll(Sort sort) {
        return defaultRepository.findAll(sort);
    }

    @Override
    public List<M> getAll(@Nullable Specification<M> specification, @Nullable Sort sort) {
        return defaultRepository.findAll(specification, sort);
    }

    @Override
    public List<M> getAll(@Nullable Specification<M> specification, @Nullable String sortQuery) {
        Sort sort = null;
        if (sortQuery != null) {
            sort = SortUtil.parseSortQuery(sortQuery);
        }

        return defaultRepository.findAll(specification, sort);
    }

    @Override
    public List<M> getAll(@Nullable String rsqlQuery, Sort sort) {
        return defaultRepository.findAllByQuery(rsqlQuery, sort);
    }

    @Override
    public List<M> getAll(@Nullable String rsqlQuery, @Nullable String sortQuery) {
        Sort sort = null;
        if (sortQuery != null) {
             sort = SortUtil.parseSortQuery(sortQuery);
        }

        return defaultRepository.findAllByQuery(rsqlQuery, sort);
    }

    @Override
    public void add(M model) {
        defaultRepository.create(model);
    }
}
