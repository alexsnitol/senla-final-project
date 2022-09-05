package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.model.house.FamilyHouse;

public interface IFamilyHouseService extends IAbstractHouseService<FamilyHouse, FamilyHouseDto> {

    FamilyHouseDto getDtoById(Long id);

    void add(RequestFamilyHouseDto requestFamilyHouseDto);

    void updateById(UpdateRequestFamilyHouseDto updateRequestFamilyHouseDto, Long id);

}
