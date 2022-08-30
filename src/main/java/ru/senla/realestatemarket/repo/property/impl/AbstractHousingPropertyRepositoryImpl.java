package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.repo.property.IAbstractHousingPropertyRepository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

@Slf4j
@Repository
public abstract class AbstractHousingPropertyRepositoryImpl<M> extends AbstractPropertyRepositoryImpl<M>
        implements IAbstractHousingPropertyRepository<M> {

    @Override
    public <T> void fetchSelection(From<T, M> from) {
        from.fetch("renovationType", JoinType.LEFT);

        super.fetchSelection(from);
    }
}
