package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestAddressDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.SortUtil;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class AddressServiceImpl extends AbstractServiceImpl<Address, Long> implements IAddressService {

    private final IAddressRepository addressRepository;
    private final IStreetRepository streetRepository;

    private final AddressMapper addressMapper;


    public AddressServiceImpl(
            IAddressRepository addressRepository,
            IStreetRepository streetRepository,
            AddressMapper addressMapper
    ) {
        this.clazz = Address.class;
        this.defaultRepository = addressRepository;

        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.addressMapper = addressMapper;
    }


    @Override
    @Transactional
    public AddressDto getDtoById(Long id) {
        return addressMapper.toAddressDto(getById(id));
    }

    @Override
    @Transactional
    public List<AddressDto> getAllDto(String rsqlQuery, String sortQuery) {
        return addressMapper.toAddressDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public List<HouseNumberDto> getAllHouseNumbersDto(Long streetId, String sortQuery) {
        List<Address> addressList
                = addressRepository.findByStreetId(streetId, SortUtil.parseSortQuery(sortQuery));

        return addressMapper.toHouseNumberDto(addressList);
    }

    @Override
    @Transactional
    public List<HouseNumberDto> getAllHouseNumbersDtoByRegionIdAndCityIdAndStreetId(
            Long regionId, Long cityId, Long streetId, String sortQuery) {
        return addressMapper.toHouseNumberDto(
                addressRepository.findByRegionIdAndCityIdAndStreetId(
                        regionId, cityId, streetId, SortUtil.parseSortQuery(sortQuery))
        );
    }

    @Override
    @Transactional
    public AddressDto add(RequestAddressDto requestAddressDto) {
        Address address = addressMapper.toAddress(requestAddressDto);

        Long streetId = requestAddressDto.getStreetId();
        setStreetByStreetId(address, streetId);


        Address addressResponse = addressRepository.create(address);

        return addressMapper.toAddressDto(addressResponse);
    }

    @Override
    @Transactional
    public AddressDto addByStreetId(RequestHouseNumberDto requestHouseNumberDto, Long streetId) {
        Address address = addressMapper.toAddress(requestHouseNumberDto);

        setStreetByStreetId(address, streetId);


        Address addressResponse = addressRepository.create(address);

        return addressMapper.toAddressDto(addressResponse);
    }

    private void setStreetByStreetId(Address address, Long streetId) {
        Street street = streetRepository.findById(streetId);
        EntityHelper.checkEntityOnNull(street, Street.class, streetId);

        address.setStreet(street);
    }

    @Override
    @Transactional
    public AddressDto addByRegionIdAndCityIdAndStreetId(
            RequestHouseNumberDto requestHouseNumberDto, Long regionId, Long cityId, Long streetId
    ) {
        Address address = addressMapper.toAddress(requestHouseNumberDto);

        setStreetByRegionIdAndCityIdAndStreetId(address, regionId, cityId, streetId);


        Address addressResponse = addressRepository.create(address);

        return addressMapper.toAddressDto(addressResponse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestAddressDto updateRequestAddressDto, Long id) {
        Address address = getById(id);

        Long streetId = updateRequestAddressDto.getStreetId();
        if (streetId != null) {
            setStreetByStreetId(address, streetId);
        }

        addressMapper.updateAddressFromUpdateRequestAddressDto(
                updateRequestAddressDto, address
        );


        addressRepository.update(address);
    }

    @Override
    @Transactional
    public void updateByStreetIdAndByHouseNumber(
            UpdateRequestAddressDto updateRequestAddressDto, Long streetId, String houseNumber
    ) {
        Address address = addressRepository.findByStreetIdAndHouseNumber(streetId, houseNumber);
        if (address == null) {
            String message = String.format(
                    "Address with house number %s in street with id %s not exist", houseNumber, streetId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        Long newStreetId = updateRequestAddressDto.getStreetId();
        if (newStreetId != null) {
            setStreetByStreetId(address, newStreetId);
        }

        addressMapper.updateAddressFromUpdateRequestAddressDto(
                updateRequestAddressDto, address
        );


        addressRepository.update(address);
    }

    @Override
    @Transactional
    public void updateByRegionIdAndCityIdAndStreetIdAndByHouseNumber(
            UpdateRequestAddressDto updateRequestAddressDto,
            Long regionId, Long cityId, Long streetId, String houseNumber
    ) {
        Address address = getAddressByRegionIdAndCityIdAndStreetIdAndHouseNumber(
                regionId, cityId, streetId, houseNumber);


        Long newStreetId = updateRequestAddressDto.getStreetId();
        if (newStreetId != null) {
            setStreetByStreetId(address, newStreetId);
        }

        addressMapper.updateAddressFromUpdateRequestAddressDto(
                updateRequestAddressDto, address
        );


        addressRepository.update(address);
    }

    private Address getAddressByRegionIdAndCityIdAndStreetIdAndHouseNumber(
            Long regionId, Long cityId, Long streetId, String houseNumber
    ) {
        Address address = addressRepository.findByRegionIdAndCityIdAndStreetIdAndHouseNumber(
                regionId, cityId, streetId, houseNumber
        );
        if (address == null) {
            String message = String.format(
                    "Address with house number %s in region with id %s" +
                            " and in city with id %s and in street with id %s not exist",
                    houseNumber, regionId, cityId, streetId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }
        return address;
    }

    private void setStreetByRegionIdAndCityIdAndStreetId(
            Address address, Long regionId, Long cityId, Long streetId
    ) {
        Street street = streetRepository.findByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);
        if (street == null) {
            String message = String.format(
                    "Street with id %s in region with id %s and in city with id %s not exist",
                    streetId, regionId, cityId);

            log.error(message);
            throw new EntityNotFoundException(message);
        }

        address.setStreet(street);
    }

}
