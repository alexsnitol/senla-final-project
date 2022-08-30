package ru.senla.realestatemarket.repo.address;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IAddressRepository extends IAbstractRepository<Address, Long> {

    List<Address> findByRegionIdAndCityIdAndStreetId(
            Long regionId, Long cityId, Long streetId, Sort sort);

    Address findByRegionIdAndCityIdAndStreetIdAndHouseNumber(
            Long regionId, Long cityId, Long streetId, String houseNumber);

    List<Address> findByStreetId
            (Long streetId, Sort sort);

    Address findByStreetIdAndHouseNumber(
            Long streetId, String houseNumber);
}
