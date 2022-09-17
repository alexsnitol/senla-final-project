package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestStreetDto;
import ru.senla.realestatemarket.model.address.Street;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class StreetMapper {
    
    public abstract StreetDto toStreetDto(Street street);
    
    public abstract List<StreetDto> toStreetDto(Collection<Street> streets);
    
    public abstract Street toStreet(StreetDto streetDto);
    
    public abstract List<Street> toStreet(Collection<StreetDto> streetDto);

    public abstract Street toStreet(RequestStreetDto requestStreetDto);

    public abstract Street toStreet(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateStreetByUpdateRequestStreetDto(
            UpdateRequestStreetDto updateRequestStreetDto,
            @MappingTarget Street street
    );
    
}
