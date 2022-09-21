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
public class FamilyHouseDto extends HouseDto {

    private Boolean swimmingPool;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyHouseDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyHouseDto that = (FamilyHouseDto) o;
        return Objects.equals(getSwimmingPool(), that.getSwimmingPool());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSwimmingPool());
    }

}
