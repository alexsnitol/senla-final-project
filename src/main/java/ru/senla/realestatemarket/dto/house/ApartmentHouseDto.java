package ru.senla.realestatemarket.dto.house;

import lombok.Data;

@Data
public class ApartmentHouseDto extends HouseDto {

    private Boolean elevator;

    private Integer numberOfApartmentProperties;

}
