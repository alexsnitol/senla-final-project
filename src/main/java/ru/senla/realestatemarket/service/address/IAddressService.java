package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestAddressDto;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IAddressService extends IAbstractService<Address, Long> {

    AddressDto getDtoById(Long id);

    List<AddressDto> getAllDto(String rsqlQuery, String sortQuery);
    List<HouseNumberDto> getAllHouseNumbersDto(Long streetId, String sortQuery);
    List<HouseNumberDto> getAllHouseNumbersDtoByRegionIdAndCityIdAndStreetId(
            Long regionId, Long cityId, Long streetId, String sortQuery);

    void add(RequestAddressDto requestAddressDto);
    void addByStreetId(RequestHouseNumberDto requestHouseNumberDto, Long streetId);
    void addByRegionIdAndCityIdAndStreetId(
            RequestHouseNumberDto requestHouseNumberDto, Long regionId, Long cityId, Long streetId);

    void updateById(
            UpdateRequestAddressDto updateRequestAddressDto, Long id);
    void updateByStreetIdAndByHouseNumber(
            UpdateRequestAddressDto updateRequestAddressDto, Long streetId, String houseNumber);
    void updateByRegionIdAndCityIdAndStreetIdAndByHouseNumber(
            UpdateRequestAddressDto updateRequestAddressDto,
            Long regionId, Long cityId, Long streetId, String houseNumber);

}
