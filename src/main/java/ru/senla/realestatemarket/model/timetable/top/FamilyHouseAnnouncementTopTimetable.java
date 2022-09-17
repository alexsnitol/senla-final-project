package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "family_house_announcement_top_timetables")
public class FamilyHouseAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_announcement_id")
    @ToString.Exclude
    private FamilyHouseAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private FamilyHouseAnnouncementTopPurchase familyHouseAnnouncementTopPurchase;


    public FamilyHouseAnnouncementTopTimetable(
            FamilyHouseAnnouncement familyHouseAnnouncement, LocalDateTime fromDt, LocalDateTime toDt) {
        super(fromDt, toDt);
        this.announcement = familyHouseAnnouncement;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FamilyHouseAnnouncementTopTimetable that = (FamilyHouseAnnouncementTopTimetable) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
