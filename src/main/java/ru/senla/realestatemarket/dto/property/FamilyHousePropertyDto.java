package ru.senla.realestatemarket.dto.property;

import lombok.Data;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;

@Data
public class FamilyHousePropertyDto extends HousingPropertyDto {

    private FamilyHouseDto familyHouse;

}
