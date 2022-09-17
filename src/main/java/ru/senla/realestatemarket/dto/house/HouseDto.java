package ru.senla.realestatemarket.dto.house;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.model.house.HouseTypeEnum;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;

@Data
public class HouseDto {

    private Long id;

    private AddressDto address;

    private Short numberOfFloors;

    private Short buildingYear;

    private String houseMaterial;

    private HousingTypeEnum housingType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HouseTypeEnum houseType;

}
