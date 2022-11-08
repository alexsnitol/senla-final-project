package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class StreetServiceImpl extends AbstractServiceImpl<Street, Long> implements IStreetService {

    private final IStreetRepository streetRepository;
    private final ICityRepository cityRepository;

    private final StreetMapper streetMapper;


    public StreetServiceImpl(
            IStreetRepository streetRepository,
            ICityRepository cityRepository,
            StreetMapper streetMapper
    ) {
        this.clazz = Street.class;
        this.defaultRepository = streetRepository;

        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
        this.streetMapper = streetMapper;
    }


    @Override
    @Transactional
    public StreetDto getDtoById(Long id) {
        return streetMapper.toStreetDto(getById(id));
    }

    @Override
    @Transactional
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
    @Transactional
    public List<StreetDto> getAllDto(String rsqlQuery, String sortQuery) {
        return streetMapper.toStreetDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public List<StreetDto> getAllDtoByCityId(Long cityId, String sortQuery) {
        return streetMapper.toStreetDto(
                streetRepository.findAllByCityId(cityId, SortUtil.parseSortQuery(sortQuery)));
    }

    @Override
    @Transactional
    public List<StreetDto> getAllDtoByRegionIdAndCityId(Long regionId, Long cityId, String sortQuery) {
        return streetMapper.toStreetDto(
                streetRepository.findByRegionIdAndCityId(regionId, cityId, SortUtil.parseSortQuery(sortQuery))
        );
    }

    @Override
    @Transactional
    public StreetDto add(RequestStreetDto requestStreetDto) {
        Street street = streetMapper.toStreet(requestStreetDto);

        Long cityId = requestStreetDto.getCityId();
        setCityById(street, cityId);


        Street streetResponse = streetRepository.create(street);

        return streetMapper.toStreetDto(streetResponse);
    }

    @Override
    @Transactional
    public StreetDto addByCityId(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long cityId) {
        Street street = streetMapper.toStreet(requestStreetWithoutCityIdDto);

        setCityById(street, cityId);


        Street streetResponse = streetRepository.create(street);

        return streetMapper.toStreetDto(streetResponse);
    }

    private void setCityById(Street street, Long cityId) {
        City city = cityRepository.findById(cityId);
        EntityHelper.checkEntityOnNull(city, City.class, cityId);

        street.setCity(city);
    }

    @Override
    @Transactional
    public StreetDto addByRegionIdAndCityId(
            RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long regionId, Long cityId
    ) {
        City city = cityRepository.findByRegionIdAndCityId(regionId, cityId);
        if (city == null) {
            String message
                    = String.format("City with id %s in region with id %s not exist", cityId, regionId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        Street street = streetMapper.toStreet(requestStreetWithoutCityIdDto);
        street.setCity(city);

        Street streetResponse = streetRepository.create(street);

        return streetMapper.toStreetDto(streetResponse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestStreetDto updateRequestStreetDto, Long id
    ) {
        Street street = getById(id);


        Long cityId = updateRequestStreetDto.getCityId();

        City city = cityRepository.findById(cityId);
        EntityHelper.checkEntityOnNull(city, City.class, cityId);


        updateFromDto(updateRequestStreetDto, street);
    }

    @Override
    @Transactional
    public void updateFromDtoByCityIdAndByStreetId(
            UpdateRequestStreetDto updateRequestStreetDto, Long cityId, Long streetId
    ) {
        Street street = streetRepository.findCityIdAndStreetId(cityId, streetId);
        if (street == null) {
            String message
                    = String.format("Street with id %s in city with id %s not exist", streetId, cityId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        updateFromDto(updateRequestStreetDto, street);
    }

    @Override
    @Transactional
    public void updateFromDtoByRegionIdAndCityIdAndByStreetId(
            UpdateRequestStreetDto updateRequestStreetDto, Long regionId, Long cityId, Long streetId
    ) {
        Street street = streetRepository.findByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);
        if (street == null) {
            String message
                    = String.format("Street with id %s in city with id %s in region with id %s not exist",
                    streetId, cityId, regionId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        updateFromDto(updateRequestStreetDto, street);
    }

    private void updateFromDto(UpdateRequestStreetDto updateRequestStreetDto, Street street) {
        streetMapper.updateStreetByUpdateRequestStreetDto(updateRequestStreetDto, street);

        streetRepository.update(street);
    }

    @Override
    @Transactional
    public void deleteByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        getDtoByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        deleteById(streetId);
    }

}
