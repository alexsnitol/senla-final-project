package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class FamilyHousePropertyDto extends HousingPropertyDto {

    private FamilyHouseDto familyHouse;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyHousePropertyDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyHousePropertyDto that = (FamilyHousePropertyDto) o;
        return Objects.equals(getFamilyHouse(), that.getFamilyHouse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFamilyHouse());
    }

}
