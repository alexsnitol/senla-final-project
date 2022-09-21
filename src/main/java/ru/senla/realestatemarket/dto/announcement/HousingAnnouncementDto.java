package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;

import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class HousingAnnouncementDto extends AnnouncementDto {

    private HousingAnnouncementTypeEnum type;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingAnnouncementDto)) {
            return false;
        }
        HousingAnnouncementDto that = (HousingAnnouncementDto) o;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }

}
