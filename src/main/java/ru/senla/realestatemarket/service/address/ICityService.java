package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface ICityService extends IAbstractService<City, Long> {

    List<CityDto> getAllDto(String rsqlQuery, String sortQuery);
    List<CityDto> getAllDtoByRegionId(Long regionId, String sortQuery);

    void add(RequestCityDto requestCityDto);
    void add(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto, Long regionId);

    boolean isExistByRegionIdAndCityId(Long regionId, Long cityId);

    void checkCityOnExistByRegionIdAndCityId(Long regionId, Long cityId);

}
