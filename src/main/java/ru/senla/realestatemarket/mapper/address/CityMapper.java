package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestCityDto;
import ru.senla.realestatemarket.model.address.City;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class CityMapper {

    public abstract CityDto toCityDto(City city);

    public abstract List<CityDto> toCityDto(Collection<City> cities);

    public abstract City toCity(CityDto cityDto);

    public abstract List<City> toCity(Collection<CityDto> cityDto);

    public abstract City toCity(RequestCityDto requestCityDto);

    public abstract City toCity(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCityByUpdateRequestCityDto(
            UpdateRequestCityDto updateRequestCityDto,
            @MappingTarget City city
    );

}
