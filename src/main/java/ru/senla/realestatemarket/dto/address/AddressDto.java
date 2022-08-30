package ru.senla.realestatemarket.dto.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AddressDto {

    private Long id;

    private RegionDto region;

    private CityDto city;

    private StreetDto street;

    private String houseNumber;

}
