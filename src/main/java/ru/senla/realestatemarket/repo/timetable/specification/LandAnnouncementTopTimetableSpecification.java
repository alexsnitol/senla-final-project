package ru.senla.realestatemarket.repo.timetable.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public class LandAnnouncementTopTimetableSpecification {

    private LandAnnouncementTopTimetableSpecification() {}


    public static Specification<LandAnnouncementTopTimetable> hasLandAnnouncementId(
            Long landAnnouncementId
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<LandAnnouncementTopTimetable, LandAnnouncement> announcementJoin
                    = root.join("announcement");

            return criteriaBuilder.equal(announcementJoin.get("id"), landAnnouncementId);
        };
    }

    public static Specification<LandAnnouncementTopTimetable> fromDtOrToDtMustBeBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.fromDtOrToDtMustBeBetweenSpecificFromAndTo(from, to);
    }


}
