package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.model.house.HouseMaterial;

@Mapper
public abstract class HouseMaterialMapper {

    public String houseMaterialToString(HouseMaterial houseMaterial) {
        return houseMaterial.getName();
    }

    public abstract HouseMaterial toHouseMaterial(RequestHouseMaterialDto requestHouseMaterialDto);

}
