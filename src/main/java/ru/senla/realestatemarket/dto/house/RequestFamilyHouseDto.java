package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestFamilyHouseDto {

    @NotNull
    private Long addressId;

    private Short numberOfFloors;

    private Short buildingYear;

    private Long houseMaterialId;

    private HousingTypeEnum housingType;

    private Boolean swimmingPool;

}
