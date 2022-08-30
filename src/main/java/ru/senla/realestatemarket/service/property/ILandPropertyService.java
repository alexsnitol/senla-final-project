package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.model.property.LandProperty;

public interface ILandPropertyService extends IAbstractPropertyService<LandProperty, LandPropertyDto> {

    void add(RequestLandPropertyDto requestLandPropertyDto, Long userId);

}
