package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.mapper.house.FamilyHouseMapper;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IFamilyHouseRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
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

    private final FamilyHouseMapper familyHouseMapper;


    public FamilyHouseServiceImpl(IFamilyHouseRepository familyHouseRepository,
                                  IHouseMaterialRepository houseMaterialRepository,
                                  IAddressRepository addressRepository,
                                  FamilyHouseMapper familyHouseMapper) {
        super(houseMaterialRepository, addressRepository);
        this.familyHouseRepository = familyHouseRepository;
        this.familyHouseMapper = familyHouseMapper;
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

    @Override
    @Transactional
    public FamilyHouseDto getDtoById(Long id) {
        return familyHouseMapper.toFamilyHouseDto(getById(id));
    }

    @Override
    @Transactional
    public void addFromDto(RequestFamilyHouseDto requestFamilyHouseDto) {
        FamilyHouse familyHouse = familyHouseMapper.toFamilyHouse(requestFamilyHouseDto);


        Long addressId = requestFamilyHouseDto.getAddressId();
        setAddressById(familyHouse, addressId);

        Long houseMaterialId = requestFamilyHouseDto.getHouseMaterialId();
        setHouseMaterialById(familyHouse, houseMaterialId);


        familyHouseRepository.create(familyHouse);
    }

    @Override
    @Transactional
    public void addFromDto(RequestFamilyHouseWithStreetIdAndHouseNumberDto requestFamilyHouseDto) {
        FamilyHouse familyHouse = familyHouseMapper.toFamilyHouse(requestFamilyHouseDto);


        Long streetId = requestFamilyHouseDto.getAddress().getStreetId();
        String houseNumber = requestFamilyHouseDto.getAddress().getHouseNumber();
        setAddressByStreetIdAndHouseNumber(familyHouse, streetId, houseNumber);

        Long houseMaterialId = requestFamilyHouseDto.getHouseMaterialId();
        setHouseMaterialById(familyHouse, houseMaterialId);


        familyHouseRepository.create(familyHouse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestFamilyHouseDto updateRequestFamilyHouseDto, Long id) {
        FamilyHouse familyHouse = getById(id);


        Long addressId = updateRequestFamilyHouseDto.getAddressId();
        if (addressId != null) {
            setAddressById(familyHouse, addressId);
        }

        Long houseMaterialId = updateRequestFamilyHouseDto.getHouseMaterialId();
        setHouseMaterialById(familyHouse, houseMaterialId);

        familyHouseMapper.updateFamilyHouseFromUpdateRequestFamilyHouseDto(
                updateRequestFamilyHouseDto, familyHouse
        );


        familyHouseRepository.update(familyHouse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto updateRequestFamilyHouseDto, Long id) {
        FamilyHouse familyHouse = getById(id);


        RequestAddressDto requestAddressDto = updateRequestFamilyHouseDto.getAddress();
        if (requestAddressDto != null) {
            Long streetId = updateRequestFamilyHouseDto.getAddress().getStreetId();
            String houseNumber = updateRequestFamilyHouseDto.getAddress().getHouseNumber();
            if (streetId != null && houseNumber != null) {
                setAddressByStreetIdAndHouseNumber(familyHouse, streetId, houseNumber);
            }
        }

        Long houseMaterialId = updateRequestFamilyHouseDto.getHouseMaterialId();
        setHouseMaterialById(familyHouse, houseMaterialId);

        familyHouseMapper.updateFamilyHouseFromUpdateRequestFamilyHouseDto(
                updateRequestFamilyHouseDto, familyHouse
        );


        familyHouseRepository.update(familyHouse);
    }

}
