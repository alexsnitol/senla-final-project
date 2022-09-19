package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;


@Getter
@Setter
public class UpdateRequestFamilyHouseWithStreetIdAndHouseNumberDto {

    private RequestAddressDto address;

    private Short numberOfFloors;

    private Short buildingYear;

    private Long houseMaterialId;

    private HousingTypeEnum housingType;

    private Boolean swimmingPool;

}
