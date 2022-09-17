package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.mapper.house.ApartmentHouseMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {ApartmentHouseMapper.class, UserMapper.class, RenovationTypeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class ApartmentPropertyMapper {

    public abstract ApartmentPropertyDto toApartmentPropertyDto(ApartmentProperty apartmentProperty);

    public abstract List<ApartmentPropertyDto> toApartmentPropertyDto(
            Collection<ApartmentProperty> apartmentProperties);

    public abstract ApartmentProperty toApartmentProperty(RequestApartmentPropertyDto requestApartmentPropertyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateApartmentPropertyFromUpdateRequestApartmentPropertyDto(
            UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto,
            @MappingTarget ApartmentProperty apartmentProperty
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateApartmentPropertyFromUpdateRequestApartmentPropertyWithUserIdOfOwnerDto(
            UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestApartmentPropertyWithUserIdOfOwnerDto,
            @MappingTarget ApartmentProperty apartmentProperty
    );

}
