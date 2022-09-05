package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_house_announcement_top_timetables")
public class FamilyHouseAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_announcement_id")
    private FamilyHouseAnnouncement announcement;

    @OneToOne(mappedBy = "timetable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FamilyHouseAnnouncementTopPurchase familyHouseAnnouncementTopPurchase;

}
