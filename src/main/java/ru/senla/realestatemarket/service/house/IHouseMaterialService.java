package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IHouseMaterialService extends IAbstractService<HouseMaterial, Long> {

    void add(RequestHouseMaterialDto requestHouseMaterialDto);

}
