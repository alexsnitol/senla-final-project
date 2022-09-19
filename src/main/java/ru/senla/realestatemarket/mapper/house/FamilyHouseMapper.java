package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.house.FamilyHouse;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {AddressMapper.class, HouseMaterialMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class FamilyHouseMapper {

    public abstract FamilyHouseDto toFamilyHouseDto(FamilyHouse familyHouse);

    public abstract List<FamilyHouseDto> toFamilyHouseDto(Collection<FamilyHouse> familyHouses);

    public abstract FamilyHouse toFamilyHouse(
            RequestFamilyHouseWithStreetIdAndHouseNumberDto requestFamilyHouseDto);

    public abstract FamilyHouse toFamilyHouse(
            RequestFamilyHouseDto requestFamilyHouseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFamilyHouseFromUpdateRequestFamilyHouseDto(
            UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto updateRequestFamilyHouseDto,
            @MappingTarget FamilyHouse familyHouse
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFamilyHouseFromUpdateRequestFamilyHouseDto(
            UpdateRequestFamilyHouseDto updateRequestFamilyHouseDto,
            @MappingTarget FamilyHouse familyHouse
    );

}
