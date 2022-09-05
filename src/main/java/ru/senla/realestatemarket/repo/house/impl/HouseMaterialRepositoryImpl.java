package ru.senla.realestatemarket.repo.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class HouseMaterialRepositoryImpl extends AbstractRepositoryImpl<HouseMaterial, Long>
        implements IHouseMaterialRepository {

    @PostConstruct
    public void init() {
        setClazz(HouseMaterial.class);
    }


}
