package ru.senla.realestatemarket.repo.timetable.top.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

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
    
    public static Specification<FamilyHouseAnnouncementTopTimetable>
    hasUserIdOfOwnerInPropertyInFamilyHouseAnnouncement(Long userIdOfOwner) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementTopTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<FamilyHouseAnnouncement, FamilyHouseProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<FamilyHouseProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }
    
    public static Specification<FamilyHouseAnnouncementTopTimetable>
    hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
            Long familyHouseAnnouncementId, Long userIdOfOwner
    ) {
        return (root, query, criteriaBuilder) -> {
            Join<FamilyHouseAnnouncementTopTimetable, FamilyHouseAnnouncement> announcementJoin
                    = root.join("announcement");
            Join<FamilyHouseAnnouncement, FamilyHouseProperty> propertyJoin
                    = announcementJoin.join("property");
            Join<FamilyHouseProperty, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(announcementJoin.get("id"), familyHouseAnnouncementId),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }

    public static Specification<FamilyHouseAnnouncementTopTimetable> concernsTheIntervalBetweenSpecificFromAndTo(
            LocalDateTime from, LocalDateTime to) {
        return GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo(from, to);
    }


}
