package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestCityDto;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface ICityService extends IAbstractService<City, Long> {

    CityDto getDtoById(Long id);
    CityDto getDtoRegionIdAndByCityId(Long regionId, Long cityId);

    List<CityDto> getAllDto(String rsqlQuery, String sortQuery);
    List<CityDto> getAllDtoByRegionId(Long regionId, String sortQuery);

    void add(RequestCityDto requestCityDto);
    void addByRegionId(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto, Long regionId);

    void updateById(UpdateRequestCityDto updateRequestCityDto, Long id);
    void updateByRegionIdAndCityId(
            UpdateRequestCityDto updateRequestCityDto, Long regionId, Long cityId);

    void deleteRegionIdAndCityById(Long regionId, Long cityId);
}
