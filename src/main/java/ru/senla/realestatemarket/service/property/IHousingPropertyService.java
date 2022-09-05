package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.HousingPropertyDto;
import ru.senla.realestatemarket.model.property.HousingProperty;

import java.util.List;

public interface IHousingPropertyService
        extends IAbstractHousingPropertyService<HousingProperty> {

    List<HousingPropertyDto> getAllDto(String rsqlQuery, String sortQuery);

    List<HousingPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

}
