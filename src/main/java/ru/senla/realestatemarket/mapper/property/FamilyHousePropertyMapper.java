package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.mapper.house.FamilyHouseMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {FamilyHouseMapper.class, UserMapper.class, RenovationTypeMapper.class})
public abstract class FamilyHousePropertyMapper {

    public abstract FamilyHousePropertyDto toFamilyHousePropertyDto(FamilyHouseProperty familyHouseProperty);

    public abstract List<FamilyHousePropertyDto> toFamilyHousePropertyDto(
            Collection<FamilyHouseProperty> familyHouseProperties);

    public abstract FamilyHouseProperty toFamilyHouseProperty(
            RequestFamilyHousePropertyDto requestFamilyHousePropertyDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFamilyHousePropertyFromUpdateRequestFamilyHouseDto(
            UpdateRequestFamilyHousePropertyDto updateRequestFamilyHousePropertyDto,
            @MappingTarget FamilyHouseProperty familyHouseProperty
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFamilyHousePropertyFromUpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto(
            UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto updateRequestFamilyHousePropertyWithUserIdOfOwnerDto,
            @MappingTarget FamilyHouseProperty familyHouseProperty
    );

}
