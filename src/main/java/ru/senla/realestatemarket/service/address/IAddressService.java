package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IAddressService extends IAbstractService<Address, Long> {

    List<AddressDto> getAllDto(String rsqlQuery, String sortQuery);

    List<HouseNumberDto> getAllHouseNumbersDto(Long streetId, String sortQuery);

    void add(RequestAddressDto requestAddressDto);
    void add(RequestHouseNumberDto requestHouseNumberDto, Long streetId);

    boolean isExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);

    void checkAddressOnExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId);
}
