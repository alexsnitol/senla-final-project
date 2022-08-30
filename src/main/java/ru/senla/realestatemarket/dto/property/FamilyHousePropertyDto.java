package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;

@Getter
@Setter
public class FamilyHousePropertyDto extends HousingPropertyDto {

    private FamilyHouseDto familyHouse;

}
