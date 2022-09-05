package ru.senla.realestatemarket.repo.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.address.IAddressRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.address.specification.AddressSpecification.hasCityId;
import static ru.senla.realestatemarket.repo.address.specification.AddressSpecification.hasHouseNumber;
import static ru.senla.realestatemarket.repo.address.specification.AddressSpecification.hasRegionId;
import static ru.senla.realestatemarket.repo.address.specification.AddressSpecification.hasStreetId;

@Slf4j
@Repository
public class AddressRepositoryImpl extends AbstractRepositoryImpl<Address, Long> implements IAddressRepository {

    @PostConstruct
    public void init() {
        setClazz(Address.class);
    }


    @Override
    public List<Address> findByRegionIdAndCityIdAndStreetId(
            Long regionId, Long cityId, Long streetId, Sort sort) {
        return findAll(
                hasRegionId(regionId)
                .and(hasCityId(cityId))
                .and(hasStreetId(streetId)),
                sort
        );
    }

    @Override
    public Address findByRegionIdAndCityIdAndStreetIdAndHouseNumber(
            Long regionId, Long cityId, Long streetId, String houseNumber) {
        return findOne(
                hasRegionId(regionId)
                .and(hasCityId(cityId))
                .and(hasStreetId(streetId))
                .and(hasHouseNumber(houseNumber))
        );
    }

    @Override
    public List<Address> findByStreetId(
            Long streetId, Sort sort) {
        return findAll(
                hasStreetId(streetId),
                sort
        );
    }

    @Override
    public Address findByStreetIdAndHouseNumber(Long streetId, String houseNumber) {
        return findOne(
                hasStreetId(streetId)
                .and(hasHouseNumber(houseNumber))
        );
    }

}
