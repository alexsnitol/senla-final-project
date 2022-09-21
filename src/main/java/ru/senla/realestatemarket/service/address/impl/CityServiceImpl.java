package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestCityDto;
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

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class CityServiceImpl extends AbstractServiceImpl<City, Long> implements ICityService {

    private final ICityRepository cityRepository;
    private final IRegionRepository regionRepository;

    private final CityMapper cityMapper;


    public CityServiceImpl(ICityRepository cityRepository,
                           IRegionRepository regionRepository,
                           CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
        this.cityMapper = cityMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(cityRepository);
        setClazz(City.class);
    }


    @Override
    @Transactional
    public CityDto getDtoById(Long id) {
        return cityMapper.toCityDto(getById(id));
    }

    @Override
    @Transactional
    public CityDto getDtoRegionIdAndByCityId(Long regionId, Long cityId) {
        City city = getByRegionIdAndByCityId(regionId, cityId);
        return cityMapper.toCityDto(city);
    }

    private City getByRegionIdAndByCityId(Long regionId, Long cityId) {
        City city = cityRepository.findByRegionIdAndCityId(regionId, cityId);
        if (city == null) {
            String message =
                    String.format("City with id %s in region with id %s not exist", cityId, regionId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }
        return city;
    }

    @Override
    @Transactional
    public List<CityDto> getAllDto(String rsqlQuery, String sortQuery) {
        return cityMapper.toCityDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public List<CityDto> getAllDtoByRegionId(Long regionId, String sortQuery) {
        return cityMapper.toCityDto(getAll(hasRegionId(regionId), sortQuery));
    }

    @Override
    @Transactional
    public void add(RequestCityDto requestCityDto) {
        City city = cityMapper.toCity(requestCityDto);

        Long regionId = requestCityDto.getRegionId();
        setRegionById(city, regionId);

        cityRepository.create(city);
    }

    @Override
    @Transactional
    public void addByRegionId(RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto, Long regionId) {
        City city = cityMapper.toCity(requestCityWithoutRegionIdDto);

        setRegionById(city, regionId);

        cityRepository.create(city);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestCityDto updateRequestCityDto, Long id) {
        City city = getById(id);

        Long regionId = updateRequestCityDto.getRegionId();
        if (regionId != null) {
            setRegionById(city, regionId);
        }

        cityMapper.updateCityByUpdateRequestCityDto(
                updateRequestCityDto, city
        );


        cityRepository.update(city);
    }

    @Override
    @Transactional
    public void updateByRegionIdAndCityId(
            UpdateRequestCityDto updateRequestCityDto, Long regionId, Long cityId
    ) {
        City city = getByRegionIdAndByCityId(regionId, cityId);

        Long newRegionId = updateRequestCityDto.getRegionId();
        if (newRegionId != null) {
            setRegionById(city, newRegionId);
        }

        cityMapper.updateCityByUpdateRequestCityDto(
                updateRequestCityDto, city
        );


        cityRepository.update(city);
    }

    private void setRegionById(City city, Long regionId) {
        Region region = regionRepository.findById(regionId);
        EntityHelper.checkEntityOnNull(region, Region.class, regionId);

        city.setRegion(region);
    }

    @Override
    @Transactional
    public void deleteRegionIdAndCityById(Long regionId, Long cityId) {
        getDtoRegionIdAndByCityId(regionId, cityId);

        deleteById(cityId);
    }

}
