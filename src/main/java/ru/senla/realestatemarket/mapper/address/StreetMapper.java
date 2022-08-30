package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.model.address.Street;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class StreetMapper {
    
    public abstract StreetDto toStreetDto(Street street);
    
    public abstract List<StreetDto> toStreetDto(Collection<Street> streets);
    
    public abstract Street toStreet(StreetDto streetDto);
    
    public abstract List<Street> toStreet(Collection<StreetDto> streetDtos);

    public abstract Street toStreet(RequestStreetDto requestStreetDto);

    public abstract Street toStreet(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto);
    
}
