package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class UpdateRequestFamilyHouseDto {

    private Long addressId;

    private Short numberOfFloors;

    private Short buildingYear;

    private Long houseMaterialId;

    private HousingTypeEnum housingType;

    private Boolean swimmingPool;

}
