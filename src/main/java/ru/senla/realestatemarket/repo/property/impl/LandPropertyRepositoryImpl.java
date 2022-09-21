package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class LandPropertyRepositoryImpl extends AbstractPropertyRepositoryImpl<LandProperty>
        implements ILandPropertyRepository {

    @PostConstruct
    public void init() {
        setClazz(LandProperty.class);
    }

}
