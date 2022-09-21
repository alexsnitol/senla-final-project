package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.HousingPropertyDto;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.property.HousingProperty;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.util.Collection;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Mapper(uses = {UserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        implementationName = "MyPropertyMapperImpl")
public abstract class PropertyMapper {

    protected ApartmentPropertyMapper apartmentPropertyMapper;
    protected FamilyHousePropertyMapper familyHousePropertyMapper;
    protected HousingPropertyMapper housingPropertyMapper;
    protected LandPropertyMapper landPropertyMapper;


    @Autowired
    public void setApartmentPropertyMapper(ApartmentPropertyMapper apartmentPropertyMapper) {
        this.apartmentPropertyMapper = apartmentPropertyMapper;
    }

    @Autowired
    public void setFamilyHousePropertyMapper(FamilyHousePropertyMapper familyHousePropertyMapper) {
        this.familyHousePropertyMapper = familyHousePropertyMapper;
    }

    @Autowired
    public void setHousingPropertyMapper(HousingPropertyMapper housingPropertyMapper) {
        this.housingPropertyMapper = housingPropertyMapper;
    }

    @Autowired
    public void setLandPropertyMapper(LandPropertyMapper landPropertyMapper) {
        this.landPropertyMapper = landPropertyMapper;
    }


    @AfterMapping
    protected void setPropertyType(Property property, @MappingTarget PropertyDto propertyDto) {
        if (property instanceof ApartmentProperty) {
            propertyDto.setPropertyType(PropertyTypeEnum.APARTMENT);
        }
        if (property instanceof FamilyHouseProperty) {
            propertyDto.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);
        }
        if (property instanceof LandProperty) {
            propertyDto.setPropertyType(PropertyTypeEnum.LAND);
        }
    }

    public abstract PropertyDto toPropertyDto(Property property);

    public abstract List<PropertyDto> toPropertyDto(Collection<Property> properties);

    @Named(value = "MappedInheritors")
    public PropertyDto toPropertyDtoWithMappedInheritors(Property property) {
        if (property instanceof ApartmentProperty) {
            ApartmentPropertyDto apartmentPropertyDto
                    = apartmentPropertyMapper.toApartmentPropertyDto((ApartmentProperty) property);

            apartmentPropertyDto.setPropertyType(PropertyTypeEnum.APARTMENT);

            return apartmentPropertyDto;
        } else if (property instanceof FamilyHouseProperty) {
            FamilyHousePropertyDto familyHousePropertyDto
                    = familyHousePropertyMapper.toFamilyHousePropertyDto((FamilyHouseProperty) property);

            familyHousePropertyDto.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);

            return familyHousePropertyDto;
        } else if (property instanceof HousingProperty) {
            HousingPropertyDto housingPropertyDto
                    = housingPropertyMapper.toHousingPropertyDto((HousingProperty) property);

            return housingPropertyDto;
        } else if (property instanceof LandProperty) {
            LandPropertyDto landPropertyDto
                    = landPropertyMapper.toLandPropertyDto((LandProperty) property);

            landPropertyDto.setPropertyType(PropertyTypeEnum.LAND);

            return landPropertyDto;
        } else {
            PropertyDto propertyDto
                    = toPropertyDto(property);

            return propertyDto;
        }
    }

    @IterableMapping(qualifiedByName = "MappedInheritors")
    public abstract List<PropertyDto> toPropertyDtoWithMappedInheritors(Collection<Property> properties);

}
