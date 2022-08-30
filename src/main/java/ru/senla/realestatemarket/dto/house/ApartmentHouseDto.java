package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentHouseDto extends HouseDto {

    private Boolean elevator;

    private Integer numberOfApartmentProperties;

}
