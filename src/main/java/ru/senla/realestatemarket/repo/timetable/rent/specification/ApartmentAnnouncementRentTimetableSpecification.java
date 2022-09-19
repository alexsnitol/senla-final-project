package ru.senla.realestatemarket.repo.timetable.rent.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;
import java.util.List;

public class ApartmentAnnouncementRentTimetableSpecification {


    private ApartmentAnnouncementRentTimetableSpecification() {}


    public static Specification<ApartmentAnnouncementRentTimetable> hasApartmentAnnouncementId(
            Long apartmentAnnouncementId
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementRentTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");

            return criteriaBuilder.equal(announcementJoin.get("id"), apartmentAnnouncementId);
        };
    }

    public static Specification<ApartmentAnnouncementRentTimetable> hasUserIdOfOwnerInPropertyInApartmentAnnouncement(
            Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementRentTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<ApartmentAnnouncement, ApartmentProperty> propertyJoin = announcementJoin.join("property");
            Join<ApartmentProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static Specification<ApartmentAnnouncementRentTimetable>
    hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementRentTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<ApartmentAnnouncement, ApartmentProperty> propertyJoin = announcementJoin.join("property");
            Join<ApartmentProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(propertyJoin.get("id"), apartmentAnnouncementId),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }

    public static Specification<ApartmentAnnouncementRentTimetable> hasUserIdOfTenant(Long userIdOfTenant) {
        return GenericAnnouncementRentTimetableSpecification.hasUserIdOfTenant(userIdOfTenant);
    }

    public static Specification<ApartmentAnnouncementRentTimetable> inIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to
    ) {
        return GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo(from, to);
    }

    public static Specification<ApartmentAnnouncementRentTimetable> hasStatusInAnnouncement(
            AnnouncementStatusEnum status
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementRentTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");

            return criteriaBuilder.equal(announcementJoin.get("status"), status);
        };
    }

    public static Specification<ApartmentAnnouncementRentTimetable> hasStatusesInAnnouncement(
            List<AnnouncementStatusEnum> statuses
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<ApartmentAnnouncementRentTimetable, ApartmentAnnouncement> announcementJoin
                    = root.join("announcement");

            return announcementJoin.get("status").in(statuses);
        };
    }

}
