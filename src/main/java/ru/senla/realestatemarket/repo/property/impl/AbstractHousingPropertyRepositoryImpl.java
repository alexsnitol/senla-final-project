package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.repo.property.IAbstractHousingPropertyRepository;

@Slf4j
@Repository
public abstract class AbstractHousingPropertyRepositoryImpl<M> extends AbstractPropertyRepositoryImpl<M>
        implements IAbstractHousingPropertyRepository<M> {

}
