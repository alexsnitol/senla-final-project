package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ApartmentPropertyDto extends HousingPropertyDto {

    private ApartmentHouseDto apartmentHouse;

    private String apartmentNumber;

    private Integer floor;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentPropertyDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentPropertyDto that = (ApartmentPropertyDto) o;
        return Objects.equals(getApartmentHouse(), that.getApartmentHouse())
                && Objects.equals(getApartmentNumber(), that.getApartmentNumber())
                && Objects.equals(getFloor(), that.getFloor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getApartmentHouse(), getApartmentNumber(), getFloor());
    }

}
