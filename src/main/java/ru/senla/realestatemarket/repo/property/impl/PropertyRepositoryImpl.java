package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.repo.property.IPropertyRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class PropertyRepositoryImpl extends AbstractPropertyRepositoryImpl<Property>
        implements IPropertyRepository {

    @PostConstruct
    public void init() {
        setClazz(Property.class);
    }

}
