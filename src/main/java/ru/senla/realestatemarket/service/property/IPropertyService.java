package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.model.property.Property;

import java.util.List;

public interface IPropertyService extends IAbstractPropertyService<Property> {

    List<PropertyDto> getAllDto(String rsqlQuery, String sortQuery);

    List<PropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);
}
