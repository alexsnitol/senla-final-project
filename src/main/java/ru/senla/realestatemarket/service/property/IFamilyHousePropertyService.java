package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

public interface IFamilyHousePropertyService
        extends IAbstractHousingPropertyService<FamilyHouseProperty, FamilyHousePropertyDto> {

    void add(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto, Long ownerId);

}
