package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;

@Slf4j
@Repository
public class RenovationTypeRepositoryImpl extends AbstractRepositoryImpl<RenovationType, Long> implements IRenovationTypeRepository {


    @PostConstruct
    public void init() {
        setClazz(RenovationType.class);
    }


}
