package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.model.address.City;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class CityMapper {

    public abstract CityDto toCityDto(City city);

    public abstract List<CityDto> toCityDto(Collection<City> cities);

    public abstract City toCity(CityDto cityDto);

    public abstract List<City> toCity(Collection<CityDto> cityDtos);

    public abstract City toCity(RequestCityDto requestCityDto);

    public abstract City toCity(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto);

}
