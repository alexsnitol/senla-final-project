package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.mapper.house.ApartmentHouseMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.property.ApartmentProperty;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {ApartmentHouseMapper.class, UserMapper.class, RenovationTypeMapper.class})
public abstract class ApartmentPropertyMapper {

    public abstract ApartmentPropertyDto toApartmentPropertyDto(ApartmentProperty apartmentProperty);

    public abstract List<ApartmentPropertyDto> toApartmentPropertyDto(
            Collection<ApartmentProperty> apartmentProperties);

    public abstract ApartmentProperty toApartmentProperty(RequestApartmentPropertyDto requestApartmentPropertyDto);

}
