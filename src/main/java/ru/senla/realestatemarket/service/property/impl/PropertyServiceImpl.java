package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.mapper.property.PropertyMapper;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.repo.property.IPropertyRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.property.IPropertyService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PropertyServiceImpl
        extends AbstractPropertyServiceImpl<Property, PropertyDto>
        implements IPropertyService {

    private final IPropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper
            = Mappers.getMapper(PropertyMapper.class);


    public PropertyServiceImpl(IPropertyRepository propertyRepository,
                               IUserRepository userRepository) {
        super(userRepository);
        this.propertyRepository = propertyRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(propertyRepository);
        setClazz(Property.class);
    }


    @Override
    @Transactional
    public List<PropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<Property> propertyList = getAll(rsqlQuery, sortQuery);

        return propertyMapper.toPropertyDtoWithMappedInheritors(propertyList);
    }

}
