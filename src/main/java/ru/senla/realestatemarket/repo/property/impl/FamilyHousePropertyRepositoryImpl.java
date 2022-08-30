package ru.senla.realestatemarket.repo.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.repo.property.IFamilyHousePropertyRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

@Slf4j
@Repository
public class FamilyHousePropertyRepositoryImpl extends AbstractHousingPropertyRepositoryImpl<FamilyHouseProperty>
        implements IFamilyHousePropertyRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseProperty.class);
    }


    @Override
    public <T> void fetchSelection(From<T, FamilyHouseProperty> from) {
        from.fetch("familyHouse", JoinType.LEFT);

        super.fetchSelection(from);
    }

}
