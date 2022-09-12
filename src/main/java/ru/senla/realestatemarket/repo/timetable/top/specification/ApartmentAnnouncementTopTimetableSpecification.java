package ru.senla.realestatemarket.repo.timetable.top.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification;

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

    public static Specification<ApartmentAnnouncementTopTimetable> hasUserIdOfOwnerInPropertyInApartmentAnnouncement(
            Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementTopTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<ApartmentAnnouncement, ApartmentProperty> propertyJoin = announcementJoin.join("property");
            Join<ApartmentProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static Specification<ApartmentAnnouncementTopTimetable>
    hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementTopTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<ApartmentAnnouncement, ApartmentProperty> propertyJoin = announcementJoin.join("property");
            Join<ApartmentProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(announcementJoin.get("id"), apartmentAnnouncementId),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }

    public static Specification<ApartmentAnnouncementTopTimetable> concernsTheIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo(from, to);
    }


}
