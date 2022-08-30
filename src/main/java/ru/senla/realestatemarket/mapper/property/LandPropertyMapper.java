package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
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

}
