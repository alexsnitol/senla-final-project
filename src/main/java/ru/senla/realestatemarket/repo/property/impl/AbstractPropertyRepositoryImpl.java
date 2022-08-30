package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.property.IAbstractPropertyRepository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

@Slf4j
@Repository
public abstract class AbstractPropertyRepositoryImpl<M> extends AbstractRepositoryImpl<M, Long>
        implements IAbstractPropertyRepository<M> {

    @Override
    public <T> void fetchSelection(From<T, M> from) {
        from.fetch("owner", JoinType.LEFT);
    }

}
