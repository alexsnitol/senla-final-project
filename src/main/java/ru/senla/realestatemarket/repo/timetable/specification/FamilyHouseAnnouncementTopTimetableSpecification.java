package ru.senla.realestatemarket.repo.timetable.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public class FamilyHouseAnnouncementTopTimetableSpecification {

    private FamilyHouseAnnouncementTopTimetableSpecification() {}


    public static Specification<FamilyHouseAnnouncementTopTimetable> hasFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementTopTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");

            return criteriaBuilder.equal(announcementJoin.get("id"), familyHouseAnnouncementId);
        };
    }

    public static Specification<FamilyHouseAnnouncementTopTimetable> fromDtOrToDtMustBeBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.fromDtOrToDtMustBeBetweenSpecificFromAndTo(from, to);
    }


}
