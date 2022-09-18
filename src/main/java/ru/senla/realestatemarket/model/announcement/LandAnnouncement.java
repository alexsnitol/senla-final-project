package ru.senla.realestatemarket.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import ru.senla.realestatemarket.model.property.LandProperty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "land_announcements")
public class LandAnnouncement extends Announcement {

    @Enumerated(EnumType.STRING)
    private NonHousingAnnouncementTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_property_id")
    private LandProperty property;

    @Formula("(" +
            " SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END" +
            " FROM land_announcement_top_timetables AS t" +
            " WHERE t.from_date_time <= now() AND t.to_date_time >= now() AND t.land_announcement_id = id" +
            ")")
    private Boolean top;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LandAnnouncement)) return false;
        if (!super.equals(o)) return false;
        LandAnnouncement that = (LandAnnouncement) o;
        return getType() == that.getType()
                && Objects.equals(getProperty(), that.getProperty())
                && Objects.equals(getTop(), that.getTop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), getProperty(), getTop());
    }

}
