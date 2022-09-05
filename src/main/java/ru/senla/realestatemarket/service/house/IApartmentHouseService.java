package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

public interface IApartmentHouseService extends IAbstractHouseService<ApartmentHouse, ApartmentHouseDto> {

    ApartmentHouseDto getDtoById(Long id);

    void add(RequestApartmentHouseDto requestApartmentHouseDto);

    void updateById(UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto, Long id);

}
