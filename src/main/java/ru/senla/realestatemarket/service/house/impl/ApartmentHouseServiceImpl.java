package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.mapper.house.ApartmentHouseMapper;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ApartmentHouseServiceImpl extends AbstractHouseServiceImpl<ApartmentHouse, ApartmentHouseDto> implements IApartmentHouseService {

    private final IApartmentHouseRepository apartmentHouseRepository;

    private final ApartmentHouseMapper apartmentHouseMapper = Mappers.getMapper(ApartmentHouseMapper.class);


    public ApartmentHouseServiceImpl(IApartmentHouseRepository apartmentHouseRepository,
                                     IHouseMaterialRepository houseMaterialRepository,
                                     IAddressRepository addressRepository) {
        super(houseMaterialRepository, addressRepository);
        this.apartmentHouseRepository = apartmentHouseRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentHouseRepository);
        setClazz(ApartmentHouse.class);
    }


    @Override
    @Transactional
    public List<ApartmentHouseDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<ApartmentHouse> houseList = getAll(rsqlQuery, sortQuery);
        return apartmentHouseMapper.toApartmentHouseDto(houseList);
    }

    @Transactional
    public void add(RequestApartmentHouseDto requestApartmentHouseDto) {
        ApartmentHouse apartmentHouse = apartmentHouseMapper.toApartmentHouse(requestApartmentHouseDto);


        Long houseMaterialId = requestApartmentHouseDto.getHouseMaterialId();
        HouseMaterial houseMaterial = houseMaterialRepository.findById(houseMaterialId);
        EntityHelper.checkEntityOnNullAfterFoundById(houseMaterial, HouseMaterial.class, houseMaterialId);

        apartmentHouse.setHouseMaterial(houseMaterial);


        Long streetId = requestApartmentHouseDto.getAddress().getStreetId();
        String houseNumber = requestApartmentHouseDto.getAddress().getHouseNumber();

        Address address = addressRepository.findByStreetIdAndHouseNumber(streetId, houseNumber);

        if (address == null) {
            throw new EntityNotFoundException(String.format(
                    "Address with street with id %s and house number %s not exist", streetId, houseNumber));
        }

        apartmentHouse.setAddress(address);


        apartmentHouseRepository.create(apartmentHouse);
    }

}
