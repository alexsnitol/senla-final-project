package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class ApartmentPropertyRepositoryImpl extends AbstractHousingPropertyRepositoryImpl<ApartmentProperty>
        implements IApartmentPropertyRepository {

    @PostConstruct
    public void init() {
        super.setClazz(ApartmentProperty.class);
    }

}
