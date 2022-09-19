package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.mapper.house.HouseMaterialMapper;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Slf4j
@Service
public class HouseMaterialServiceImpl extends AbstractServiceImpl<HouseMaterial, Long>
        implements IHouseMaterialService {

    private final IHouseMaterialRepository houseMaterialRepository;

    private final HouseMaterialMapper houseMaterialMapper;


    public HouseMaterialServiceImpl(IHouseMaterialRepository houseMaterialRepository,
                                    HouseMaterialMapper houseMaterialMapper) {
        this.houseMaterialRepository = houseMaterialRepository;
        this.houseMaterialMapper = houseMaterialMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(houseMaterialRepository);
        setClazz(HouseMaterial.class);
    }

    @Override
    @Transactional
    public void add(RequestHouseMaterialDto requestHouseMaterialDto) {
        HouseMaterial houseMaterial = houseMaterialMapper.toHouseMaterial(requestHouseMaterialDto);

        houseMaterialRepository.create(houseMaterial);
    }

    @Override
    @Transactional
    public void updateById(RequestHouseMaterialDto requestHouseMaterialDto, Long id) {
        HouseMaterial houseMaterial = getById(id);

        houseMaterialMapper.updateHouseMaterialFromRequestHouseMaterial(
                requestHouseMaterialDto, houseMaterial
        );


        houseMaterialRepository.update(houseMaterial);
    }

}
