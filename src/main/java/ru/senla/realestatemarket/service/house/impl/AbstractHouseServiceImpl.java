package ru.senla.realestatemarket.service.house.impl;

import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.house.IAbstractHouseService;

public abstract class AbstractHouseServiceImpl<M, D>
        extends AbstractServiceImpl<M, Long>
        implements IAbstractHouseService<M, D> {

    protected final IHouseMaterialRepository houseMaterialRepository;
    protected final IAddressRepository addressRepository;

    protected AbstractHouseServiceImpl(IHouseMaterialRepository houseMaterialRepository,
                                       IAddressRepository addressRepository) {
        this.houseMaterialRepository = houseMaterialRepository;
        this.addressRepository = addressRepository;
    }

}
