package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

import java.util.List;

public interface IFamilyHousePropertyService
        extends IAbstractHousingPropertyService<FamilyHouseProperty> {

    FamilyHousePropertyDto getDtoById(Long id);

    List<FamilyHousePropertyDto> getAllDto(String rsqlQuery, String sortQuery);
    List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    void add(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto, Long userIdOfOwner);
    void addFromCurrentUser(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto);

    void updateById(UpdateRequestFamilyHousePropertyDto updateRequestFamilyHousePropertyDto, Long id);

}
