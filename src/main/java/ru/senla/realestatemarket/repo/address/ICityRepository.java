package ru.senla.realestatemarket.repo.address;

import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.repo.IAbstractRepository;

public interface ICityRepository extends IAbstractRepository<City, Long> {
    City findByRegionIdAndCityId(Long regionId, Long cityId);
}
