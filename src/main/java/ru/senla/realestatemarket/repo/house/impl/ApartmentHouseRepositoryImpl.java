package ru.senla.realestatemarket.repo.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class ApartmentHouseRepositoryImpl extends AbstractHouseRepositoryImpl<ApartmentHouse>
        implements IApartmentHouseRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentHouse.class);
    }


}