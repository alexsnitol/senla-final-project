package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.model.house.FamilyHouse;

public interface IFamilyHouseService extends IAbstractHouseService<FamilyHouse, FamilyHouseDto> {

    FamilyHouseDto getDtoById(Long id);

    void addFromDto(RequestFamilyHouseDto requestFamilyHouseDto);
    void addFromDto(RequestFamilyHouseWithStreetIdAndHouseNumberDto requestFamilyHouseDto);

    void updateById(UpdateRequestFamilyHouseDto updateRequestFamilyHouseDto, Long id);
    void updateById(UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto updateRequestFamilyHouseDto, Long id);

}
