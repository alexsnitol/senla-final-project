package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.mapper.address.CityMapper;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.repo.address.ICityRepository;
import ru.senla.realestatemarket.repo.address.IRegionRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.ICityService;
import ru.senla.realestatemarket.service.helper.EntityHelper;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.address.specification.CitySpecification.hasRegionId;

@Slf4j
@Service
public class CityServiceImpl extends AbstractServiceImpl<City, Long> implements ICityService {

    private final ICityRepository cityRepository;
    private final IRegionRepository regionRepository;

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);


    public CityServiceImpl(ICityRepository cityRepository, IRegionRepository regionRepository) {
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(cityRepository);
        setClazz(City.class);
    }


    @Override
    public List<CityDto> getAllDto(String rsqlQuery, String sortQuery) {
        return cityMapper.toCityDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    public List<CityDto> getAllDtoByRegionId(Long regionId, String sortQuery) {
        return cityMapper.toCityDto(getAll(hasRegionId(regionId), sortQuery));
    }

    @Override
    @Transactional
    public void add(RequestCityDto requestCityDto) {
        City city = cityMapper.toCity(requestCityDto);
        Long regionId = requestCityDto.getRegionId();

        add(city, regionId);
    }

    @Transactional
    public void add(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto, Long regionId) {
        City city = cityMapper.toCity(requestCityWithoutRegionIdDto);

        add(city, regionId);
    }

    private void add(City city, Long regionId) {
        Region region = regionRepository.findById(regionId);
        EntityHelper.checkEntityOnNullAfterFoundById(region, Region.class, regionId);

        city.setRegion(region);

        cityRepository.create(city);
    }

    @Override
    public boolean isExistByRegionIdAndCityId(Long regionId, Long cityId) {
        City city = cityRepository.findByRegionIdAndCityId(regionId, cityId);

        return city != null;
    }

    @Override
    public void checkCityOnExistByRegionIdAndCityId(Long regionId, Long cityId) {
        if (!isExistByRegionIdAndCityId(regionId, cityId)) {
            throw new EntityNotFoundException(
                    String.format("City with id %s not exist in region with id %s", cityId, regionId));
        }
    }
}
