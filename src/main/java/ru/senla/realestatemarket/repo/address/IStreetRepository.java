package ru.senla.realestatemarket.repo.address;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IStreetRepository extends IAbstractRepository<Street, Long> {

    List<Street> findByRegionIdAndCityId(Long regionId, Long cityId, Sort sort);

    Street findByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);
}
