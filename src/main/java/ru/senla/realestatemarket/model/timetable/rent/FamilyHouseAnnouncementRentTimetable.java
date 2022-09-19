package ru.senla.realestatemarket.model.timetable.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_house_announcement_rent_timetables")
public class FamilyHouseAnnouncementRentTimetable extends AnnouncementRentTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_announcement_id")
    private FamilyHouseAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FamilyHouseAnnouncementRentPurchase familyHouseAnnouncementRentPurchase;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyHouseAnnouncementRentTimetable)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyHouseAnnouncementRentTimetable that = (FamilyHouseAnnouncementRentTimetable) o;
        return Objects.equals(getAnnouncement(), that.getAnnouncement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAnnouncement());
    }

}
