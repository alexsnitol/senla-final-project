package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class FamilyHouseAnnouncementDto extends HousingAnnouncementDto {

    private FamilyHousePropertyDto property;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyHouseAnnouncementDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyHouseAnnouncementDto that = (FamilyHouseAnnouncementDto) o;
        return Objects.equals(getProperty(), that.getProperty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProperty());
    }

}
