package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.HousingPropertyDto;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.property.HousingProperty;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {UserMapper.class, RenovationTypeMapper.class})
public abstract class HousingPropertyMapper {

    private final ApartmentPropertyMapper apartmentPropertyMapper
            = Mappers.getMapper(ApartmentPropertyMapper.class);
    private final FamilyHousePropertyMapper familyHousePropertyMapper
            = Mappers.getMapper(FamilyHousePropertyMapper.class);


    public abstract HousingPropertyDto toHousingPropertyDto(HousingProperty housingProperty);

    public abstract List<HousingPropertyDto> toHousingPropertyDto(Collection<HousingProperty> housingProperties);

    @Named(value = "MappedInheritors")
    public HousingPropertyDto toHousingPropertyDtoWithMappedInheritors(HousingProperty housingProperty) {
        if (housingProperty instanceof ApartmentProperty) {
            ApartmentPropertyDto apartmentPropertyDto
                    = apartmentPropertyMapper.toApartmentPropertyDto((ApartmentProperty) housingProperty);

            apartmentPropertyDto.setPropertyType(PropertyTypeEnum.APARTMENT);

            return apartmentPropertyDto;
        } else if (housingProperty instanceof FamilyHouseProperty) {
            FamilyHousePropertyDto familyHousePropertyDto
                    = familyHousePropertyMapper.toFamilyHousePropertyDto((FamilyHouseProperty) housingProperty);

            familyHousePropertyDto.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);

            return familyHousePropertyDto;
        } else {
            HousingPropertyDto housingPropertyDto
                    = toHousingPropertyDto(housingProperty);

            return housingPropertyDto;
        }
    }

    @IterableMapping(qualifiedByName = "MappedInheritors")
    public abstract List<HousingPropertyDto> toHousingPropertyDtoWithMappedInheritors(List<HousingProperty> housingPropertyList);
}
