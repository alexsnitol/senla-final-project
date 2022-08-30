package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressWithoutHouseNumber {

    private String region;

    private String city;

    private String street;

}
