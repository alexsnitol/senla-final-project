package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;

@Getter
@Setter
public class ApartmentPropertyDto extends HousingPropertyDto {

    private ApartmentHouseDto apartmentHouse;

    private String apartmentNumber;

    private Integer floor;

}
