package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestStreetDto;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IStreetService extends IAbstractService<Street, Long> {

    StreetDto getDtoById(Long id);
    StreetDto getDtoByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);

    List<StreetDto> getAllDto(String rsqlQuery, String sortQuery);
    List<StreetDto> getAllDtoByCityId(Long cityId, String sortQuery);
    List<StreetDto> getAllDtoByRegionIdAndCityId(Long regionId, Long cityId, String sortQuery);

    void add(RequestStreetDto requestStreetDto);
    void addByCityId(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long cityId);
    void addByRegionIdAndCityId(
            RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long regionId, Long cityId);

    void updateById(UpdateRequestStreetDto updateRequestStreetDto, Long id);
    void updateFromDtoByCityIdAndByStreetId(
            UpdateRequestStreetDto updateRequestStreetDto, Long cityId, Long streetId);
    void updateFromDtoByRegionIdAndCityIdAndByStreetId(
            UpdateRequestStreetDto updateRequestStreetDto, Long regionId, Long cityId, Long streetId);

    void deleteByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);

}
