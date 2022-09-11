package ru.senla.realestatemarket.repo.timetable.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public class ApartmentAnnouncementTopTimetableSpecification {

    private ApartmentAnnouncementTopTimetableSpecification() {}


    public static Specification<ApartmentAnnouncementTopTimetable> hasApartmentAnnouncementId(
            Long apartmentAnnouncementId
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementTopTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");

            return criteriaBuilder.equal(announcementJoin.get("id"), apartmentAnnouncementId);
        };
    }

    public static Specification<ApartmentAnnouncementTopTimetable> fromDtOrToDtMustBeBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.fromDtOrToDtMustBeBetweenSpecificFromAndTo(from, to);
    }


}
