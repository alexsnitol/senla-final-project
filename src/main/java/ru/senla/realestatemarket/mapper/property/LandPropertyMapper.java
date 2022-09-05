package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.LandProperty;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {AddressMapper.class, UserMapper.class})
public abstract class LandPropertyMapper {

    public abstract LandPropertyDto toLandPropertyDto(LandProperty landProperty);

    public abstract List<LandPropertyDto> toLandPropertyDto(Collection<LandProperty> landProperties);

    public abstract LandProperty toLandProperty(RequestLandPropertyDto requestLandPropertyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateLandPropertyFromUpdateRequestLandPropertyDto(
            UpdateRequestLandPropertyDto updateRequestLandPropertyDto,
            @MappingTarget LandProperty landProperty
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateLandPropertyFromUpdateRequestLandPropertyWithUserIdOfOwnerDto(
            UpdateRequestLandPropertyWithUserIdOfOwnerDto updateRequestLandPropertyWithUserIdOfOwnerDto,
            @MappingTarget LandProperty landProperty
    );

}
