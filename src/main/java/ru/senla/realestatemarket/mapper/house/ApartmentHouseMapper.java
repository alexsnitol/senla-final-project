package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {AddressMapper.class, HouseMaterialMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class ApartmentHouseMapper {

    public abstract ApartmentHouseDto toApartmentHouseDto(ApartmentHouse apartmentHouse);

    public abstract List<ApartmentHouseDto> toApartmentHouseDto(Collection<ApartmentHouse> apartmentHouses);

    public abstract ApartmentHouse toApartmentHouse(
            RequestApartmentHouseWithStreetIdAndHouseNumberDto requestApartmentHouseDto);

    public abstract ApartmentHouse toApartmentHouse(
            RequestApartmentHouseDto requestApartmentHouseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateApartmentHouseFromUpdateRequestApartmentHouse(
            UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto updateRequestApartmentHouseDto,
            @MappingTarget ApartmentHouse apartmentHouse
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateApartmentHouseFromUpdateRequestApartmentHouse(
            UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto,
            @MappingTarget ApartmentHouse apartmentHouse
    );

}
