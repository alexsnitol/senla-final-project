package ru.senla.realestatemarket.repo.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class ApartmentHouseRepositoryImpl extends AbstractHouseRepositoryImpl<ApartmentHouse> implements IApartmentHouseRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentHouse.class);
    }


    @Override
    protected <T> void fetchSelection(From<T, ApartmentHouse> from) {
        // fetch did not need it
    }
}