package ru.senla.realestatemarket.model.purchase.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "land_announcement_top_purchases")
public class LandAnnouncementTopPurchase extends AnnouncementTopPurchase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_announcement_top_timetable_id")
    private LandAnnouncementTopTimetable timetable;

}
