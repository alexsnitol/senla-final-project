package ru.senla.realestatemarket.dto.property;

import lombok.Data;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;

@Data
public class ApartmentPropertyDto extends HousingPropertyDto {

    private ApartmentHouseDto apartmentHouse;

    private String apartmentNumber;

    private Integer floor;

}
