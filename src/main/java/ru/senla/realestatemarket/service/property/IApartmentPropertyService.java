package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import java.util.List;

public interface IApartmentPropertyService
        extends IAbstractHousingPropertyService<ApartmentProperty> {

    ApartmentPropertyDto getDtoById(Long id);
    ApartmentPropertyDto getDtoByIdOfCurrentUser(Long id);

    List<ApartmentPropertyDto> getAllDto(String rsqlQuery, String sortQuery);
    List<ApartmentPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    void add(RequestApartmentPropertyWithUserIdOfOwnerDto requestApartmentPropertyWithUserIdOfOwnerDto);
    void addFromCurrentUser(RequestApartmentPropertyDto requestApartmentPropertyDto);

    void updateById(
            UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestApartmentPropertyWithUserIdOfOwnerDto,
            Long id);

    void setDeletedStatusByIdOfCurrentUser(Long id);

    void updateByIdOfCurrentUser(UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto, Long id);
}
