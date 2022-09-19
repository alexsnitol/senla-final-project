package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

public interface IApartmentHouseService extends IAbstractHouseService<ApartmentHouse, ApartmentHouseDto> {

    ApartmentHouseDto getDtoById(Long id);

    void addFromDto(RequestApartmentHouseDto requestApartmentHouseDto);
    void addFromDto(RequestApartmentHouseWithStreetIdAndHouseNumberDto requestApartmentHouseDto);

    void updateById(UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto, Long id);
    void updateById(UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto updateRequestApartmentHouseDto, Long id);

}
