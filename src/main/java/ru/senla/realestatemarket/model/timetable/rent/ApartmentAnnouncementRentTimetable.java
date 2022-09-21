package ru.senla.realestatemarket.model.timetable.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartment_announcement_rent_timetables")
public class ApartmentAnnouncementRentTimetable extends AnnouncementRentTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_announcement_id")
    private ApartmentAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ApartmentAnnouncementRentPurchase apartmentAnnouncementRentPurchase;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentAnnouncementRentTimetable)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentAnnouncementRentTimetable that = (ApartmentAnnouncementRentTimetable) o;
        return Objects.equals(getAnnouncement(), that.getAnnouncement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAnnouncement());
    }

}
