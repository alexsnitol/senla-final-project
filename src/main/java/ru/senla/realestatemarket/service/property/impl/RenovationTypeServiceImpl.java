package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.mapper.property.RenovationTypeMapper;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;

import javax.transaction.Transactional;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class RenovationTypeServiceImpl
        extends AbstractServiceImpl<RenovationType, Long>
        implements IRenovationTypeService {

    private final IRenovationTypeRepository renovationTypeRepository;

    private final RenovationTypeMapper renovationTypeMapper;


    public RenovationTypeServiceImpl(
            IRenovationTypeRepository renovationTypeRepository,
            RenovationTypeMapper renovationTypeMapper
    ) {
        this.clazz = RenovationType.class;
        this.defaultRepository = renovationTypeRepository;

        this.renovationTypeRepository = renovationTypeRepository;
        this.renovationTypeMapper = renovationTypeMapper;
    }


    @Override
    @Transactional
    public RenovationType add(RequestRenovationTypeDto requestRenovationTypeDto) {
        RenovationType renovationType = renovationTypeMapper.toRenovationType(requestRenovationTypeDto);

        return renovationTypeRepository.create(renovationType);
    }

    @Override
    @Transactional
    public void updateById(RequestRenovationTypeDto requestRenovationTypeDto, Long id) {
        RenovationType renovationType = getById(id);

        renovationTypeMapper.updateRenovationTypeFromRequestRenovationTypeDto(
                requestRenovationTypeDto, renovationType
        );


        renovationTypeRepository.update(renovationType);
    }

}
