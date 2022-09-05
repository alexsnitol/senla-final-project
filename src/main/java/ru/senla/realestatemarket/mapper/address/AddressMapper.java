package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.InlineAddressDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestAddressDto;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {RegionMapper.class, CityMapper.class, StreetMapper.class})
public abstract class AddressMapper {

    public InlineAddressDto toInlineAddressDto(Address address) {
        InlineAddressDto inlineAddressDto = new InlineAddressDto();

        String inlineAddress = "";

        Region region = address.getRegion();
        inlineAddress += region;

        City city = address.getCity();
        inlineAddress += ", " + city;

        Street street = address.getStreet();
        inlineAddress += ", " + street;

        String houseNumber = address.getHouseNumber();
        if (houseNumber != null) {
            inlineAddress = ", " + houseNumber;
        }

        inlineAddressDto.setAddress(inlineAddress);

        return inlineAddressDto;
    }

    public abstract AddressDto toAddressDto(Address address);

    public abstract List<AddressDto> toAddressDto(Collection<Address> addresses);

    public abstract Address toAddress(AddressDto addressDto);

    public abstract Address toAddress(RequestAddressDto requestAddressDto);

    public abstract HouseNumberDto toHouseNumberDto(Address address);

    public abstract List<HouseNumberDto> toHouseNumberDto(Collection<Address> addresses);

    public abstract Address toAddress(RequestHouseNumberDto requestHouseNumberDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateAddressFromRequestHouseNumberDto(
            RequestHouseNumberDto requestHouseNumberDto,
            @MappingTarget Address address
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateAddressFromUpdateRequestAddressDto(
            UpdateRequestAddressDto updateRequestAddressDto,
            @MappingTarget Address address
    );

}
