package ru.senla.realestatemarket.repo.house.impl;

import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.house.IAbstractHouseRepository;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public abstract class AbstractHouseRepositoryImpl<M> extends AbstractRepositoryImpl<M, Long>
        implements IAbstractHouseRepository<M> {
}
