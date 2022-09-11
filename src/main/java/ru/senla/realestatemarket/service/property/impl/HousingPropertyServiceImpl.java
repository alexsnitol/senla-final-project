package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.HousingPropertyDto;
import ru.senla.realestatemarket.mapper.property.HousingPropertyMapper;
import ru.senla.realestatemarket.model.property.HousingProperty;
import ru.senla.realestatemarket.repo.property.IHousingPropertyRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IHousingPropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class HousingPropertyServiceImpl
        extends AbstractServiceImpl<HousingProperty, Long>
        implements IHousingPropertyService {

    private final IHousingPropertyRepository housingPropertyRepository;

    private final HousingPropertyMapper housingPropertyMapper
            = Mappers.getMapper(HousingPropertyMapper.class);


    public HousingPropertyServiceImpl(IHousingPropertyRepository housingPropertyRepository) {
        this.housingPropertyRepository = housingPropertyRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(housingPropertyRepository);
        setClazz(HousingProperty.class);
    }


    @Override
    @Transactional
    public List<HousingPropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<HousingProperty> housingPropertyList = getAll(rsqlQuery, sortQuery);

        return housingPropertyMapper.toHousingPropertyDtoWithMappedInheritors(housingPropertyList);
    }

    @Override
    @Transactional
    public List<HousingPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<HousingProperty> housingPropertyList
                = housingPropertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return housingPropertyMapper.toHousingPropertyDto(housingPropertyList);
    }


}
