package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Alexander Slotin (@alexsnitol)
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "apartment_announcement_top_timetables")
public class ApartmentAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_announcement_id")
    @ToString.Exclude
    private ApartmentAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private ApartmentAnnouncementTopPurchase apartmentAnnouncementTopPurchase;


    public ApartmentAnnouncementTopTimetable(
            ApartmentAnnouncement apartmentAnnouncement, LocalDateTime fromDt, LocalDateTime toDt) {
        super(fromDt, toDt);
        this.announcement = apartmentAnnouncement;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApartmentAnnouncementTopTimetable)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ApartmentAnnouncementTopTimetable that = (ApartmentAnnouncementTopTimetable) o;
        return Objects.equals(getAnnouncement(), that.getAnnouncement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAnnouncement());
    }

}
