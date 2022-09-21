package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.house.House;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.house.IAbstractHouseService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
public abstract class AbstractHouseServiceImpl<M extends IModel<Long>, D>
        extends AbstractServiceImpl<M, Long>
        implements IAbstractHouseService<M, D> {

    protected final IHouseMaterialRepository houseMaterialRepository;
    protected final IAddressRepository addressRepository;

    protected AbstractHouseServiceImpl(IHouseMaterialRepository houseMaterialRepository,
                                       IAddressRepository addressRepository) {
        this.houseMaterialRepository = houseMaterialRepository;
        this.addressRepository = addressRepository;
    }


    protected void setAddressById(House house, Long addressId) {
        Address address = addressRepository.findById(addressId);
        EntityHelper.checkEntityOnNull(address, Address.class, addressId);

        house.setAddress(address);
    }

    protected void setHouseMaterialById(House house, Long houseMaterialId) {
        HouseMaterial houseMaterial = houseMaterialRepository.findById(houseMaterialId);
        EntityHelper.checkEntityOnNull(houseMaterial, HouseMaterial.class, houseMaterialId);

        house.setHouseMaterial(houseMaterial);
    }

    protected void setAddressByStreetIdAndHouseNumber(House house, Long streetId, String houseNumber) {
        Address address = addressRepository.findByStreetIdAndHouseNumber(streetId, houseNumber);

        if (address == null) {
            String message = String.format(
                    "Address with street with id %s and house number %s not exist", streetId, houseNumber);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        house.setAddress(address);
    }

}
