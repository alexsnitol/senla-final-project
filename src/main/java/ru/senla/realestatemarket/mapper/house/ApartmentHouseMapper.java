package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.house.ApartmentHouse;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {AddressMapper.class, HouseMaterialMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ApartmentHouseMapper {

    public abstract ApartmentHouseDto toApartmentHouseDto(ApartmentHouse apartmentHouse);

    public abstract List<ApartmentHouseDto> toApartmentHouseDto(Collection<ApartmentHouse> apartmentHouses);

    public abstract ApartmentHouse toApartmentHouse(RequestApartmentHouseDto requestApartmentHouseDto);

}
