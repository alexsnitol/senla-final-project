package ru.senla.realestatemarket.dto.house;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class ApartmentHouseDto extends HouseDto {

    private Boolean elevator;

    private Integer numberOfApartmentProperties;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentHouseDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentHouseDto that = (ApartmentHouseDto) o;
        return Objects.equals(getElevator(), that.getElevator())
                && Objects.equals(getNumberOfApartmentProperties(), that.getNumberOfApartmentProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElevator(), getNumberOfApartmentProperties());
    }

}
