package ru.senla.realestatemarket.model.timetable.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


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

}
