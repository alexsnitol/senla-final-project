package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IStreetService extends IAbstractService<Street, Long> {

    List<StreetDto> getAllDto(String rsqlQuery, String sortQuery);
    List<StreetDto> getAllDtoByCityId(Long cityId, String sortQuery);

    void add(RequestStreetDto requestStreetDto);
    void add(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long streetId);

    boolean isExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);
    void checkStreetOnExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);
}
