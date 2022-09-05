package ru.senla.realestatemarket.repo.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.repo.house.IFamilyHouseRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class FamilyHouseRepositoryImpl extends AbstractHouseRepositoryImpl<FamilyHouse> implements IFamilyHouseRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouse.class);
    }


}
