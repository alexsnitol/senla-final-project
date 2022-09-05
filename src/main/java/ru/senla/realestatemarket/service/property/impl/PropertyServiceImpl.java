package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.mapper.property.PropertyMapper;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.repo.property.IPropertyRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.property.IPropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PropertyServiceImpl
        extends AbstractPropertyServiceImpl<Property>
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

    @Override
    public List<PropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<Property> propertyList
                = propertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return propertyMapper.toPropertyDtoWithMappedInheritors(propertyList);
    }

}
