package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

import java.util.List;

public interface IFamilyHousePropertyService
        extends IAbstractHousingPropertyService<FamilyHouseProperty> {

    FamilyHousePropertyDto getDtoById(Long id);
    FamilyHousePropertyDto getDtoByIdOfCurrentUser(Long id);

    List<FamilyHousePropertyDto> getAllDto(String rsqlQuery, String sortQuery);
    List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    FamilyHousePropertyDto addFromDto(RequestFamilyHousePropertyWithUserIdOfOwnerDto requestDto);
    FamilyHousePropertyDto addFromDtoFromCurrentUser(RequestFamilyHousePropertyDto requestDto);

    void updateByIdFromDto(UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto updateRequestDto, Long id);
    void updateFromDtoByPropertyIdOfCurrentUser(UpdateRequestFamilyHousePropertyDto updateRequestDto, Long id);

}
