package ru.senla.realestatemarket.model.timetable.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

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
@Table(name = "apartment_announcement_rent_timetables")
public class ApartmentAnnouncementRentTimetable extends AnnouncementRentTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_announcement_id")
    private ApartmentAnnouncement announcement;

}
