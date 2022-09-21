package ru.senla.realestatemarket.dto.house;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.model.house.HouseTypeEnum;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class HouseDto {

    private Long id;

    private AddressDto address;

    private Short numberOfFloors;

    private Short buildingYear;

    private String houseMaterial;

    private HousingTypeEnum housingType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HouseTypeEnum houseType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseDto)) {
            return false;
        }
        HouseDto houseDto = (HouseDto) o;
        return Objects.equals(getId(), houseDto.getId())
                && Objects.equals(getAddress(), houseDto.getAddress())
                && Objects.equals(getNumberOfFloors(), houseDto.getNumberOfFloors())
                && Objects.equals(getBuildingYear(), houseDto.getBuildingYear())
                && Objects.equals(getHouseMaterial(), houseDto.getHouseMaterial())
                && getHousingType() == houseDto.getHousingType()
                && getHouseType() == houseDto.getHouseType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getNumberOfFloors(), getBuildingYear(),
                getHouseMaterial(), getHousingType(), getHouseType());
    }

}
