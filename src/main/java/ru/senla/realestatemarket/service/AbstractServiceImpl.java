package ru.senla.realestatemarket.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.IAbstractRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.SortUtil;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
public abstract class AbstractServiceImpl<M extends IModel<I>, I> implements IAbstractService<M, I> {

    @Setter
    protected IAbstractRepository<M, I> defaultRepository;
    protected Class<M> clazz;


    public void setClazz(final Class<M> clazzToSet) {
        clazz = clazzToSet;
    }


    @Override
    @Transactional
    public M getOne(Specification<M> specification) {
        M model = defaultRepository.findOne(specification);
        EntityHelper.checkEntityOnNull(model, clazz, null);

        return model;
    }

    @Override
    @Transactional
    public M getById(I id) {
        M model = defaultRepository.findById(id);
        EntityHelper.checkEntityOnNull(model, clazz, id);

        return model;
    }

    @Override
    @Transactional
    public List<M> getAll() {
        return defaultRepository.findAll();
    }

    @Override
    @Transactional
    public List<M> getAll(Specification<M> specification) {
        return defaultRepository.findAll(specification);
    }

    @Override
    @Transactional
    public List<M> getAll(Sort sort) {
        return defaultRepository.findAll(sort);
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable Specification<M> specification, @Nullable Sort sort) {
        return defaultRepository.findAll(specification, sort);
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable Specification<M> specification, @Nullable String sortQuery) {
        return defaultRepository.findAll(specification, SortUtil.parseSortQuery(sortQuery));
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable String rsqlQuery, @Nullable Sort sort) {
        return defaultRepository.findAllByQuery(rsqlQuery, sort);
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable Specification<M> specification, @Nullable String rsqlQuery, @Nullable Sort sort) {
        return defaultRepository.findAllByQuery(specification, rsqlQuery, sort);
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable String rsqlQuery, @Nullable String sortQuery) {
        return defaultRepository.findAllByQuery(rsqlQuery, SortUtil.parseSortQuery(sortQuery));
    }

    @Override
    @Transactional
    public List<M> getAll(@Nullable Specification<M> specification,
                          @Nullable String rsqlQuery,
                          @Nullable String sortQuery
    ) {
        return defaultRepository.findAllByQuery(specification, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
    }

    @Override
    @Transactional
    public M add(M model) {
        defaultRepository.create(model);
        return model;
    }

    @Override
    @Transactional
    public M update(M model) {
        defaultRepository.update(model);
        return model;
    }

    @Override
    @Transactional
    public M updateById(M changedModel, I id) {
        M model = defaultRepository.findById(id);
        EntityHelper.checkEntityOnNull(model, clazz, id);

        changedModel.setId(id);

        defaultRepository.update(changedModel);

        return changedModel;
    }

    @Override
    @Transactional
    public void deleteById(I id) {
        M model = defaultRepository.findById(id);
        EntityHelper.checkEntityOnNull(model, clazz, id);

        defaultRepository.delete(model);
    }

}
