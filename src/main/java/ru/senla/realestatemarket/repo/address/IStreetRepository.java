package ru.senla.realestatemarket.repo.address;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IStreetRepository extends IAbstractRepository<Street, Long> {


    List<Street> findAllByCityId(Long cityId, Sort sort);

    List<Street> findByRegionIdAndCityId(Long regionId, Long cityId, Sort sort);

    Street findCityIdAndStreetId(Long cityId, Long streetId);

    Street findByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);

}
