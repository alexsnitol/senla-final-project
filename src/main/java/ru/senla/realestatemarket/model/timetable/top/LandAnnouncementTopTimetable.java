package ru.senla.realestatemarket.model.timetable.top;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "land_announcement_top_timetables")
public class LandAnnouncementTopTimetable extends AnnouncementTopTimetable {

    @ManyToOne(fetch = FetchType.LAZY)
    private LandAnnouncement announcement;

}
