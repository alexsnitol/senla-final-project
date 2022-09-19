package ru.senla.realestatemarket.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HousingAnnouncement extends Announcement {

    @Enumerated(EnumType.STRING)
    private HousingAnnouncementTypeEnum type;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingAnnouncement)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HousingAnnouncement that = (HousingAnnouncement) o;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }

}
