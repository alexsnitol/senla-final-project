package ru.senla.realestatemarket.repo.timetable.rent.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

public class FamilyHouseAnnouncementRentTimetableSpecification {


    private FamilyHouseAnnouncementRentTimetableSpecification() {}
    
    
    public static Specification<FamilyHouseAnnouncementRentTimetable> hasFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementRentTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");
            
            return criteriaBuilder.equal(announcementJoin.get("id"), familyHouseAnnouncementId);
        };
    }
    
    public static Specification<FamilyHouseAnnouncementRentTimetable>
    hasUserIdOfOwnerInPropertyInFamilyHouseAnnouncement(
            Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementRentTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<FamilyHouseAnnouncement, FamilyHouseProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<FamilyHouseProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static Specification<FamilyHouseAnnouncementRentTimetable>
    hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long familyHouseAnnouncementId, Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementRentTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<FamilyHouseAnnouncement, FamilyHouseProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<FamilyHouseProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(propertyJoin.get("id"), familyHouseAnnouncementId),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }

    public static Specification<FamilyHouseAnnouncementRentTimetable> hasUserIdOfTenant(Long userIdOfTenant) {
        return GenericAnnouncementRentTimetableSpecification.hasUserIdOfTenant(userIdOfTenant);
    }
    
    public static Specification<FamilyHouseAnnouncementRentTimetable> inIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to
    ) {
        return GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo(from, to);
    }
    
}
