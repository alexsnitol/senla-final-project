package ru.senla.realestatemarket.model.purchase.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family_house_announcement_top_purchases")
public class FamilyHouseAnnouncementTopPurchase extends AnnouncementTopPurchase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_announcement_top_timetable_id")
    private FamilyHouseAnnouncementTopTimetable timetable;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyHouseAnnouncementTopPurchase)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FamilyHouseAnnouncementTopPurchase that = (FamilyHouseAnnouncementTopPurchase) o;
        return Objects.equals(getTimetable(), that.getTimetable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTimetable());
    }

}
