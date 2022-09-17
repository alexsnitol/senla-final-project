package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.model.house.HouseMaterial;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class HouseMaterialMapper {

    public String houseMaterialToString(HouseMaterial houseMaterial) {
        return houseMaterial.getName();
    }

    public abstract HouseMaterial toHouseMaterial(RequestHouseMaterialDto requestHouseMaterialDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateHouseMaterialFromRequestHouseMaterial(
            RequestHouseMaterialDto requestHouseMaterialDto,
            @MappingTarget HouseMaterial houseMaterial
    );

}
