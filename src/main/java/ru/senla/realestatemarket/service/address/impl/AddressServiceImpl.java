package ru.senla.realestatemarket.service.address.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.SortUtil;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class AddressServiceImpl extends AbstractServiceImpl<Address, Long> implements IAddressService {

    private final IAddressRepository addressRepository;
    private final IStreetRepository streetRepository;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);


    public AddressServiceImpl(IAddressRepository addressRepository,
                              IStreetRepository streetRepository) {
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(addressRepository);
        setClazz(Address.class);
    }


    @Override
    public List<AddressDto> getAllDto(String rsqlQuery, String sortQuery) {
        return addressMapper.toAddressDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    public List<HouseNumberDto> getAllHouseNumbersDto(Long streetId, String sortQuery) {
        Sort sort = null;
        if (sortQuery != null) {
            sort = SortUtil.parseSortQuery(sortQuery);
        }

        List<Address> addressList = addressRepository
                .findByStreetId(streetId, sort);

        return addressMapper.toHouseNumberDto(addressList);
    }

    @Override
    @Transactional
    public void add(RequestAddressDto requestAddressDto) {
        Address address = addressMapper.toAddress(requestAddressDto);
        Long streetId = requestAddressDto.getStreetId();

        add(address, streetId);
    }

    @Override
    @Transactional
    public void add(RequestHouseNumberDto requestHouseNumberDto, Long streetId) {
        Address address = addressMapper.toAddress(requestHouseNumberDto);

        add(address, streetId);
    }

    private void add(Address address, Long streetId) {
        Street street = streetRepository.findById(streetId);
        EntityHelper.checkEntityOnNullAfterFoundById(street, Street.class, streetId);

        address.setStreet(street);

        addressRepository.create(address);
    }

    @Override
    public boolean isExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        List<Address> address = addressRepository
                .findByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId, null);

        return !address.isEmpty();
    }

    @Override
    public void checkAddressOnExistByRegionIdAndCityIdAndStreetId(Long regionId, Long cityId, Long streetId) {
        if (!isExistByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId)) {
            throw new EntityNotFoundException(String.format(
                    "Address with region with id %s and city with id %s and street with id %s not exist",
                    regionId, cityId, streetId));
        }
    }


}
