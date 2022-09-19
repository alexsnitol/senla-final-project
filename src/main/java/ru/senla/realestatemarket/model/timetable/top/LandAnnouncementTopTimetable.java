package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.purchase.top.LandAnnouncementTopPurchase;

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
@Table(name = "land_announcement_top_timetables")
public class LandAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_announcement_id")
    @ToString.Exclude
    private LandAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private LandAnnouncementTopPurchase landAnnouncementTopPurchase;


    public LandAnnouncementTopTimetable(LandAnnouncement announcement, LocalDateTime fromDt, LocalDateTime toDt) {
        super(fromDt, toDt);
        this.announcement = announcement;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandAnnouncementTopTimetable)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LandAnnouncementTopTimetable that = (LandAnnouncementTopTimetable) o;
        return Objects.equals(getAnnouncement(), that.getAnnouncement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAnnouncement());
    }

}
