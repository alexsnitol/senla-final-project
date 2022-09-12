package ru.senla.realestatemarket.repo.timetable.top.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification;

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

    public static Specification<LandAnnouncementTopTimetable> hasUserIdOfOwnerInPropertyInLandAnnouncement(
            Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<LandAnnouncementTopTimetable, LandAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<LandAnnouncement, LandProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<LandProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static Specification<LandAnnouncementTopTimetable>
    hasLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long landAnnouncementId, Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<LandAnnouncementTopTimetable, LandAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<LandAnnouncement, LandProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<LandProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(announcementJoin.get("id"), landAnnouncementId),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }

    public static Specification<LandAnnouncementTopTimetable> concernsTheIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo(from, to);
    }


}
