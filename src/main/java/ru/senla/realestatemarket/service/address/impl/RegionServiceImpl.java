package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.mapper.address.RegionMapper;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.repo.address.IRegionRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.IRegionService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class RegionServiceImpl extends AbstractServiceImpl<Region, Long> implements IRegionService {

    private final IRegionRepository regionRepository;

    private final RegionMapper regionMapper = Mappers.getMapper(RegionMapper.class);


    public RegionServiceImpl(IRegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(regionRepository);
        setClazz(Region.class);
    }


    @Override
    public RegionDto getDtoById(Long id) {
        return regionMapper.toRegionDto(getById(id));
    }

    @Override
    public List<RegionDto> getAllDto(String rsqlQuery, String sortQuery) {
        return regionMapper.toRegionDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public void add(RequestRegionDto requestRegionDto) {
        Region region = regionMapper.toRegion(requestRegionDto);

        regionRepository.create(region);
    }

    @Override
    @Transactional
    public void updateById(RequestRegionDto requestRegionDto, Long id) {
        Region region = getById(id);

        regionMapper.updateRegionFromRequestRegionDto(
                requestRegionDto, region
        );


        regionRepository.update(region);
    }

}
