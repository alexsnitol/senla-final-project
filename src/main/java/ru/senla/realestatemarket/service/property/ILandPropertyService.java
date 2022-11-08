package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.model.property.LandProperty;

import java.util.List;

public interface ILandPropertyService extends IAbstractPropertyService<LandProperty> {

    LandPropertyDto getDtoById(Long id);
    LandPropertyDto getDtoByIdOfCurrentUser(Long id);

    List<LandPropertyDto> getAllDto(String rsqlQuery, String sortQuery);
    List<LandPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    LandPropertyDto addFromDto(RequestLandPropertyWithUserIdOfOwnerDto requestDto);
    LandPropertyDto addFromDtoFromCurrentUser(RequestLandPropertyDto requestDto);

    void updateFromDtoById(UpdateRequestLandPropertyWithUserIdOfOwnerDto updateRequestDto, Long id);
    void updateFromDtoByPropertyIdOfCurrentUser(UpdateRequestLandPropertyDto updateRequestDto, Long id);

}
