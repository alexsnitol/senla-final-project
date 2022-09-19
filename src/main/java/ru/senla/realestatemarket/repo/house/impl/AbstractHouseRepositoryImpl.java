package ru.senla.realestatemarket.repo.house.impl;

import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.house.IAbstractHouseRepository;

public abstract class AbstractHouseRepositoryImpl<M> extends AbstractRepositoryImpl<M, Long>
        implements IAbstractHouseRepository<M> {
}
