package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.HousingProperty;
import ru.senla.realestatemarket.repo.property.IHousingPropertyRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class HousingPropertyRepositoryImpl extends AbstractHousingPropertyRepositoryImpl<HousingProperty>
        implements IHousingPropertyRepository {

    @PostConstruct
    public void init() {
        setClazz(HousingProperty.class);
    }


}
