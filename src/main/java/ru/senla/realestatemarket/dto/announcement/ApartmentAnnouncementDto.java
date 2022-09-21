package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class ApartmentAnnouncementDto extends HousingAnnouncementDto {

    private ApartmentPropertyDto property;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentAnnouncementDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentAnnouncementDto that = (ApartmentAnnouncementDto) o;
        return Objects.equals(getProperty(), that.getProperty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProperty());
    }

}
