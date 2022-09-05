package ru.senla.realestatemarket.repo.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.address.IRegionRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class RegionRepositoryImpl extends AbstractRepositoryImpl<Region, Long> implements IRegionRepository {

    @PostConstruct
    public void init() {
        setClazz(Region.class);
    }


}
