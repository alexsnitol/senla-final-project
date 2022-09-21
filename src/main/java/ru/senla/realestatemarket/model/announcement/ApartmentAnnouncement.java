package ru.senla.realestatemarket.model.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment_announcements")
public class ApartmentAnnouncement extends HousingAnnouncement {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_property_id")
    private ApartmentProperty property;

    @Formula("(" +
            " SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END" +
            " FROM apartment_announcement_top_timetables AS t" +
            " WHERE t.from_date_time <= now() AND t.to_date_time >= now() AND t.apartment_announcement_id = id" +
            ")")
    private Boolean top;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "announcement")
    private List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "announcement")
    private List<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentAnnouncement)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentAnnouncement that = (ApartmentAnnouncement) o;
        return Objects.equals(getProperty(), that.getProperty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProperty());
    }

}
