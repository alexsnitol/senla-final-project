package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestStreetDto;
import ru.senla.realestatemarket.mapper.address.StreetMapper;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.address.ICityRepository;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.IStreetService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.SortUtil;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class StreetServiceImpl extends AbstractServiceImpl<Street, Long> implements IStreetService {

    private final IStreetRepository streetRepository;
    private final ICityRepository cityRepository;

    private final StreetMapper streetMapper;


    public StreetServiceImpl(IStreetRepository streetRepository,
                             ICityRepository cityRepository,
                             StreetMapper streetMapper
    ) {
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
        this.streetMapper = streetMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(streetRepository);
        setClazz(Street.class);
    }


    @Override
    public StreetDto getDtoById(Long id) {
        return streetMapper.toStreetDto(getById(id));
    }

    @Override
    public StreetDto getDtoByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        Street street = streetRepository.findByRegionIdAndCityIdAndStreetId(
                regionId, cityId, streetId);

        if (street == null) {
            String message = String.format(
                    "Street with id %s in region with id %s and in city with id %s not exist",
                    streetId, regionId, cityId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        return streetMapper.toStreetDto(street);
    }

    @Override
    public List<StreetDto> getAllDto(String rsqlQuery, String sortQuery) {
        return streetMapper.toStreetDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    public List<StreetDto> getAllDtoByCityId(Long cityId, String sortQuery) {
        return streetMapper.toStreetDto(
                streetRepository.findAllByCityId(cityId, SortUtil.parseSortQuery(sortQuery)));
    }

    @Override
    public List<StreetDto> getAllDtoByRegionIdAndCityId(Long regionId, Long cityId, String sortQuery) {
        return streetMapper.toStreetDto(
                streetRepository.findByRegionIdAndCityId(regionId, cityId, SortUtil.parseSortQuery(sortQuery))
        );
    }

    @Override
    @Transactional
    public void add(RequestStreetDto requestStreetDto) {
        Street street = streetMapper.toStreet(requestStreetDto);

        Long cityId = requestStreetDto.getCityId();
        setCityById(street, cityId);


        streetRepository.create(street);
    }

    @Override
    @Transactional
    public void addByCityId(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long cityId) {
        Street street = streetMapper.toStreet(requestStreetWithoutCityIdDto);

        setCityById(street, cityId);


        streetRepository.create(street);
    }

    private void setCityById(Street street, Long cityId) {
        City city = cityRepository.findById(cityId);
        EntityHelper.checkEntityOnNull(city, City.class, cityId);

        street.setCity(city);
    }

    @Override
    @Transactional
    public void addByRegionIdAndCityId(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long regionId, Long cityId) {
        City city = cityRepository.findByRegionIdAndCityId(regionId, cityId);
        if (city == null) {
            String message
                    = String.format("City with id %s in region with id %s not exist", cityId, regionId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        Street street = streetMapper.toStreet(requestStreetWithoutCityIdDto);
        street.setCity(city);

        streetRepository.create(street);
    }

    @Override
    public void updateById(UpdateRequestStreetDto updateRequestStreetDto, Long id) {

    }

    @Override
    public void updateByCityIdAndByStreetId(UpdateRequestStreetDto updateRequestStreetDto, Long cityId, Long streetId) {

    }

    @Override
    public void updateByRegionIdAndCityIdAndByStreetId(UpdateRequestStreetDto updateRequestStreetDto, Long regionId, Long cityId, Long streetId) {

    }

    @Override
    public void deleteByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        getDtoByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        deleteById(streetId);
    }

}
