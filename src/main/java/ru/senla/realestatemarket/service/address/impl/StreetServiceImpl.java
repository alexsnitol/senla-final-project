package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.mapper.address.StreetMapper;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.address.ICityRepository;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.IStreetService;
import ru.senla.realestatemarket.service.helper.EntityHelper;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.address.specification.StreetSpecification.hasCityId;

@Slf4j
@Service
public class StreetServiceImpl extends AbstractServiceImpl<Street, Long> implements IStreetService {

    private final IStreetRepository streetRepository;
    private final ICityRepository cityRepository;

    private final StreetMapper streetMapper = Mappers.getMapper(StreetMapper.class);


    public StreetServiceImpl(IStreetRepository streetRepository, ICityRepository cityRepository) {
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(streetRepository);
        setClazz(Street.class);
    }


    @Override
    public List<StreetDto> getAllDto(String rsqlQuery, String sortQuery) {
        return streetMapper.toStreetDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    public List<StreetDto> getAllDtoByCityId(Long cityId, String sortQuery) {
        return streetMapper.toStreetDto(getAll(hasCityId(cityId), sortQuery));
    }

    @Override
    @Transactional
    public void add(RequestStreetDto requestStreetDto) {
        Street street = streetMapper.toStreet(requestStreetDto);
        Long cityId = requestStreetDto.getCityId();

        add(street, cityId);
    }

    @Override
    @Transactional
    public void add(RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto, Long streetId) {
        Street street = streetMapper.toStreet(requestStreetWithoutCityIdDto);

        add(street, streetId);
    }

    @Override
    public boolean isExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        Street street = streetRepository.findByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        return street != null;
    }

    @Override
    public void checkStreetOnExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        if (!isExistByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId)) {
            throw new EntityNotFoundException(String.format(
                    "Street with region with id %s and city with id %s and street with id %s not exist",
                    regionId, cityId, streetId));
        }
    }

    private void add(Street street, Long cityId) {
        City city = cityRepository.findById(cityId);
        EntityHelper.checkEntityOnNullAfterFoundById(city, City.class, cityId);

        street.setCity(city);


        streetRepository.create(street);
    }

}
