package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

import java.util.Objects;

@Getter
@Setter
@ToString
public class LandAnnouncementDto extends AnnouncementDto {

    private NonHousingAnnouncementTypeEnum type;

    private LandPropertyDto property;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandAnnouncementDto)) {
            return false;
        }
        LandAnnouncementDto that = (LandAnnouncementDto) o;
        return getType() == that.getType() && Objects.equals(getProperty(), that.getProperty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getProperty());
    }

}
