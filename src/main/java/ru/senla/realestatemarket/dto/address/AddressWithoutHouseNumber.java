package ru.senla.realestatemarket.dto.address;

import lombok.Data;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
public class AddressWithoutHouseNumber {

    private String region;

    private String city;

    private String street;

}
