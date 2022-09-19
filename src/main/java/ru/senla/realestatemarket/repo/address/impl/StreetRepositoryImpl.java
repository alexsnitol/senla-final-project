package ru.senla.realestatemarket.repo.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.address.IStreetRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.address.specification.StreetSpecification.hasCityId;
import static ru.senla.realestatemarket.repo.address.specification.StreetSpecification.hasId;
import static ru.senla.realestatemarket.repo.address.specification.StreetSpecification.hasRegionId;

@Slf4j
@Repository
public class StreetRepositoryImpl extends AbstractRepositoryImpl<Street, Long> implements IStreetRepository {

    @PostConstruct
    public void init() {
        setClazz(Street.class);
    }


    @Override
    public List<Street> findAllByCityId(Long cityId, Sort sort) {
        return findAll(
                hasCityId(cityId),
                sort
        );
    }

    @Override
    public List<Street> findByRegionIdAndCityId(Long regionId, Long cityId, Sort sort) {
        return findAll(
                hasRegionId(regionId)
                .and(hasCityId(cityId))
        );
    }

    @Override
    public Street findCityIdAndStreetId(Long cityId, Long streetId) {
        return findOne(hasCityId(cityId)
                .and(hasId(streetId))
        );
    }

    @Override
    public Street findByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        return findOne(
                hasRegionId(regionId)
                .and(hasCityId(cityId))
                .and(hasId(streetId))
        );
    }

}
