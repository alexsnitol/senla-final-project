package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartment_announcement_top_timetables")
public class ApartmentAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    private ApartmentAnnouncement announcement;

}
