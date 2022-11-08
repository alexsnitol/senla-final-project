package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.mapper.property.PropertyMapper;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.repo.property.IPropertyRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IPropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class PropertyServiceImpl
        extends AbstractServiceImpl<Property, Long>
        implements IPropertyService {

    private final IPropertyRepository propertyRepository;
    private final UserUtil userUtil;

    private final PropertyMapper propertyMapper;


    public PropertyServiceImpl(
            IPropertyRepository propertyRepository,
            UserUtil userUtil,
            PropertyMapper propertyMapper
    ) {
        this.clazz = Property.class;
        this.defaultRepository = propertyRepository;

        this.propertyRepository = propertyRepository;
        this.userUtil = userUtil;
        this.propertyMapper = propertyMapper;
    }


    @Override
    @Transactional
    public List<PropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<Property> propertyList = getAll(rsqlQuery, sortQuery);

        return propertyMapper.toPropertyDtoWithMappedInheritors(propertyList);
    }

    @Override
    @Transactional
    public List<PropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<Property> propertyList
                = propertyRepository.findAllByUserIdOfOwner(userUtil.getCurrentUserId(), rsqlQuery, sort);

        return propertyMapper.toPropertyDtoWithMappedInheritors(propertyList);
    }

}
