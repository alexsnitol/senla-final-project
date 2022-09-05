package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.mapper.property.RenovationTypeMapper;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Slf4j
@Service
public class RenovationTypeServiceImpl
        extends AbstractServiceImpl<RenovationType, Long>
        implements IRenovationTypeService {

    private final IRenovationTypeRepository renovationTypeRepository;

    private final RenovationTypeMapper renovationTypeMapper = Mappers.getMapper(RenovationTypeMapper.class);


    public RenovationTypeServiceImpl(IRenovationTypeRepository renovationTypeRepository) {
        this.renovationTypeRepository = renovationTypeRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(renovationTypeRepository);
        setClazz(RenovationType.class);
    }


    @Override
    @Transactional
    public void add(RequestRenovationTypeDto requestRenovationTypeDto) {
        RenovationType renovationType = renovationTypeMapper.toRenovationType(requestRenovationTypeDto);

        renovationTypeRepository.create(renovationType);
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
