package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

public interface IApartmentPropertyService
        extends IAbstractHousingPropertyService<ApartmentProperty, ApartmentPropertyDto> {

    void add(RequestApartmentPropertyDto requestApartmentPropertyDto, Long ownerId);

}
