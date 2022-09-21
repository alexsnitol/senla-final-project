package ru.senla.realestatemarket.repo.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.house.House;
import ru.senla.realestatemarket.repo.house.IHouseRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class HouseRepositoryImpl extends AbstractHouseRepositoryImpl<House> implements IHouseRepository {

    @PostConstruct
    public void init() {
        setClazz(House.class);
    }


}
