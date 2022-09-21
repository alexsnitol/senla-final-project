package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.house.HouseDto;
import ru.senla.realestatemarket.mapper.house.HouseMapper;
import ru.senla.realestatemarket.model.house.House;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.repo.house.IHouseRepository;
import ru.senla.realestatemarket.service.house.IHouseService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class HouseServiceImpl extends AbstractHouseServiceImpl<House, HouseDto> implements IHouseService {

    private final IHouseRepository houseRepository;

    private final HouseMapper houseMapper;


    public HouseServiceImpl(IHouseMaterialRepository houseMaterialRepository,
                            IAddressRepository addressRepository,
                            IHouseRepository houseRepository,
                            HouseMapper houseMapper) {
        super(houseMaterialRepository, addressRepository);
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(houseRepository);
        setClazz(House.class);
    }


    @Override
    @Transactional
    public List<HouseDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<House> houseList = getAll(rsqlQuery, sortQuery);
        return houseMapper.toHouseDtoWithMappedInheritors(houseList);
    }
}
