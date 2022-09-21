package ru.senla.realestatemarket.service.house.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto;
import ru.senla.realestatemarket.mapper.house.ApartmentHouseMapper;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class ApartmentHouseServiceImpl extends AbstractHouseServiceImpl<ApartmentHouse, ApartmentHouseDto>
        implements IApartmentHouseService {

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
    @Transactional
    public ApartmentHouseDto getDtoById(Long id) {
        return apartmentHouseMapper.toApartmentHouseDto(getById(id));
    }

    @Override
    @Transactional
    public void addFromDto(RequestApartmentHouseDto requestApartmentHouseDto) {
        ApartmentHouse apartmentHouse = apartmentHouseMapper.toApartmentHouse(requestApartmentHouseDto);


        Long addressId = requestApartmentHouseDto.getAddressId();
        setAddressById(apartmentHouse, addressId);

        Long houseMaterialId = requestApartmentHouseDto.getHouseMaterialId();
        setHouseMaterialById(apartmentHouse, houseMaterialId);


        apartmentHouseRepository.create(apartmentHouse);
    }

    @Override
    @Transactional
    public void addFromDto(RequestApartmentHouseWithStreetIdAndHouseNumberDto requestApartmentHouseDto) {
        ApartmentHouse apartmentHouse = apartmentHouseMapper.toApartmentHouse(requestApartmentHouseDto);


        Long streetId = requestApartmentHouseDto.getAddress().getStreetId();
        String houseNumber = requestApartmentHouseDto.getAddress().getHouseNumber();
        setAddressByStreetIdAndHouseNumber(apartmentHouse, streetId, houseNumber);

        Long houseMaterialId = requestApartmentHouseDto.getHouseMaterialId();
        setHouseMaterialById(apartmentHouse, houseMaterialId);


        apartmentHouseRepository.create(apartmentHouse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto, Long id) {
        ApartmentHouse apartmentHouse = getById(id);

        Long houseMaterialId = updateRequestApartmentHouseDto.getHouseMaterialId();
        if (houseMaterialId != null) {
            setHouseMaterialById(apartmentHouse, houseMaterialId);
        }

        Long addressId = updateRequestApartmentHouseDto.getAddressId();
        if (addressId != null) {
            setAddressById(apartmentHouse, addressId);
        }

        apartmentHouseMapper.updateApartmentHouseFromUpdateRequestApartmentHouse(
                updateRequestApartmentHouseDto, apartmentHouse
        );


        apartmentHouseRepository.update(apartmentHouse);
    }

    @Override
    @Transactional
    public void updateById(
            UpdateRequestApartmentHouseWithStreetIdAndHouseNumberDto updateRequestApartmentHouseDto, Long id
    ) {
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
