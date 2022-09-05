package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.model.property.LandProperty;

import java.util.List;

public interface ILandPropertyService extends IAbstractPropertyService<LandProperty> {

    LandPropertyDto getDtoById(Long id);

    List<LandPropertyDto> getAllDto(String rsqlQuery, String sortQuery);
    List<LandPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    void add(RequestLandPropertyDto requestLandPropertyDto, Long userIdOfOwner);
    void addFromCurrentUser(RequestLandPropertyDto requestLandPropertyDto);

    void updateById(UpdateRequestLandPropertyDto updateRequestLandPropertyDto, Long id);

}
