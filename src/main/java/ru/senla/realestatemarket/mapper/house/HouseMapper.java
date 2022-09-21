package ru.senla.realestatemarket.mapper.house;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.HouseDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.house.House;
import ru.senla.realestatemarket.model.house.HouseTypeEnum;

import java.util.Collection;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Mapper(uses = {AddressMapper.class, HouseMaterialMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class HouseMapper {

    protected ApartmentHouseMapper apartmentHouseMapper;
    protected FamilyHouseMapper familyHouseMapper;


    @Autowired
    public void setApartmentHouseMapper(ApartmentHouseMapper apartmentHouseMapper) {
        this.apartmentHouseMapper = apartmentHouseMapper;
    }

    @Autowired
    public void setFamilyHouseMapper(FamilyHouseMapper familyHouseMapper) {
        this.familyHouseMapper = familyHouseMapper;
    }


    @AfterMapping
    protected void setHouseType(House house, @MappingTarget HouseDto houseDto) {
        if (house instanceof ApartmentHouse) {
            houseDto.setHouseType(HouseTypeEnum.APARTMENT_HOUSE);
        }
        if (house instanceof FamilyHouse) {
            houseDto.setHouseType(HouseTypeEnum.FAMILY_HOUSE);
        }
    }

    public abstract HouseDto toHouseDto(House house);

    public abstract List<HouseDto> toHouseDto(Collection<House> houseList);

    @Named(value = "MappedInheritors")
    public HouseDto toHouseDtoWithMappedInheritors(House house) {
        if (house instanceof ApartmentHouse) {
            ApartmentHouseDto apartmentHouseDto = apartmentHouseMapper.toApartmentHouseDto((ApartmentHouse) house);

            apartmentHouseDto.setHouseType(HouseTypeEnum.APARTMENT_HOUSE);

            return apartmentHouseDto;
        } else if (house instanceof FamilyHouse) {
            FamilyHouseDto familyHouseDto = familyHouseMapper.toFamilyHouseDto((FamilyHouse) house);

            familyHouseDto.setHouseType(HouseTypeEnum.FAMILY_HOUSE);

            return familyHouseDto;
        } else {
            return toHouseDto(house);
        }
    }

    @IterableMapping(qualifiedByName = "MappedInheritors")
    public abstract List<HouseDto> toHouseDtoWithMappedInheritors(Collection<House> houses);

}
