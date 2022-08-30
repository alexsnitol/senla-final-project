package ru.senla.realestatemarket.repo.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.address.ICityRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

import static ru.senla.realestatemarket.repo.address.specification.CitySpecification.hasId;
import static ru.senla.realestatemarket.repo.address.specification.CitySpecification.hasRegionId;

@Slf4j
@Repository
public class CityRepositoryImpl extends AbstractRepositoryImpl<City, Long> implements ICityRepository {

    @PostConstruct
    public void init() {
        setClazz(City.class);
    }


    @Override
    protected <T> void fetchSelection(From<T, City> from) {
        // fetch did not need it
    }

    @Override
    public City findByRegionIdAndCityId(Long regionId, Long cityId) {
        return findOne(hasRegionId(regionId).and(hasId(cityId)));
    }
}
