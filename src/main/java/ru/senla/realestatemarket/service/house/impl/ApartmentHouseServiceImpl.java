package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
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

    private final ApartmentHouseMapper apartmentHouseMapper;


    public ApartmentHouseServiceImpl(IApartmentHouseRepository apartmentHouseRepository,
                                     IHouseMaterialRepository houseMaterialRepository,
                                     IAddressRepository addressRepository,
                                     ApartmentHouseMapper apartmentHouseMapper) {
        super(houseMaterialRepository, addressRepository);
        this.apartmentHouseRepository = apartmentHouseRepository;
        this.apartmentHouseMapper = apartmentHouseMapper;
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

    @Override
    public ApartmentHouseDto getDtoById(Long id) {
        return apartmentHouseMapper.toApartmentHouseDto(getById(id));
    }

    @Transactional
    public void add(RequestApartmentHouseDto requestApartmentHouseDto) {
        ApartmentHouse apartmentHouse = apartmentHouseMapper.toApartmentHouse(requestApartmentHouseDto);

        Long houseMaterialId = requestApartmentHouseDto.getHouseMaterialId();
        setHouseMaterialById(apartmentHouse, houseMaterialId);

        Long streetId = requestApartmentHouseDto.getAddress().getStreetId();
        String houseNumber = requestApartmentHouseDto.getAddress().getHouseNumber();
        setAddressByStreetIdAndHouseNumber(apartmentHouse, streetId, houseNumber);


        apartmentHouseRepository.create(apartmentHouse);
    }

    private void setAddressByStreetIdAndHouseNumber(ApartmentHouse apartmentHouse, Long streetId, String houseNumber) {
        Address address = addressRepository.findByStreetIdAndHouseNumber(streetId, houseNumber);

        if (address == null) {
            String message = String.format(
                    "Address with street with id %s and house number %s not exist", streetId, houseNumber);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        apartmentHouse.setAddress(address);
    }

    private void setHouseMaterialById(ApartmentHouse apartmentHouse, Long houseMaterialId) {
        HouseMaterial houseMaterial = houseMaterialRepository.findById(houseMaterialId);
        EntityHelper.checkEntityOnNull(houseMaterial, HouseMaterial.class, houseMaterialId);

        apartmentHouse.setHouseMaterial(houseMaterial);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto, Long id) {
        ApartmentHouse apartmentHouse = getById(id);

        Long houseMaterialId = updateRequestApartmentHouseDto.getHouseMaterialId();
        if (houseMaterialId != null) {
            setHouseMaterialById(apartmentHouse, houseMaterialId);
        }

        RequestAddressDto requestAddressDto = updateRequestApartmentHouseDto.getAddress();
        if (requestAddressDto != null) {
            Long streetId = updateRequestApartmentHouseDto.getAddress().getStreetId();
            String houseNumber = updateRequestApartmentHouseDto.getAddress().getHouseNumber();
            if (streetId != null && houseNumber != null) {
                setAddressByStreetIdAndHouseNumber(apartmentHouse, streetId, houseNumber);
            }
        }

        apartmentHouseMapper.updateApartmentHouseFromUpdateRequestApartmentHouse(
                updateRequestApartmentHouseDto, apartmentHouse
        );


        apartmentHouseRepository.update(apartmentHouse);
    }

}
