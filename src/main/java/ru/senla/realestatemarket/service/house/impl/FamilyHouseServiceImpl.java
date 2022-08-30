package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.mapper.house.FamilyHouseMapper;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IFamilyHouseRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.house.IFamilyHouseService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
public class FamilyHouseServiceImpl
        extends AbstractHouseServiceImpl<FamilyHouse, FamilyHouseDto>
        implements IFamilyHouseService {

    private final IFamilyHouseRepository familyHouseRepository;

    private final FamilyHouseMapper familyHouseMapper = Mappers.getMapper(FamilyHouseMapper.class);


    public FamilyHouseServiceImpl(IFamilyHouseRepository familyHouseRepository,
                                  IHouseMaterialRepository houseMaterialRepository,
                                  IAddressRepository addressRepository) {
        super(houseMaterialRepository, addressRepository);
        this.familyHouseRepository = familyHouseRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseRepository);
        setClazz(FamilyHouse.class);
    }


    @Override
    @Transactional
    public List<FamilyHouseDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouse> familyHouseList = getAll(rsqlQuery, sortQuery);
        return familyHouseMapper.toFamilyHouseDto(familyHouseList);
    }

    @Transactional
    public void add(RequestFamilyHouseDto requestFamilyHouseDto) {
        FamilyHouse familyHouse = familyHouseMapper.toFamilyHouse(requestFamilyHouseDto);


        Long houseMaterialId = requestFamilyHouseDto.getHouseMaterialId();
        HouseMaterial houseMaterial = houseMaterialRepository.findById(houseMaterialId);
        EntityHelper.checkEntityOnNullAfterFoundById(houseMaterial, HouseMaterial.class, houseMaterialId);

        familyHouse.setHouseMaterial(houseMaterial);


        Long streetId = requestFamilyHouseDto.getAddress().getStreetId();
        String houseNumber = requestFamilyHouseDto.getAddress().getHouseNumber();

        Address address = addressRepository.findByStreetIdAndHouseNumber(streetId, houseNumber);

        familyHouse.setAddress(address);


        familyHouseRepository.create(familyHouse);
    }

}
