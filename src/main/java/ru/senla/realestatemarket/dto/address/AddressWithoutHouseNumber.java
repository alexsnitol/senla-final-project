package ru.senla.realestatemarket.dto.address;

import lombok.Data;

@Data
public class AddressWithoutHouseNumber {

    private String region;

    private String city;

    private String street;

}
