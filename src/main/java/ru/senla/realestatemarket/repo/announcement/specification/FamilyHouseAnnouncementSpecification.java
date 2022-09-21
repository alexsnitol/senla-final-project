package ru.senla.realestatemarket.repo.announcement.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class FamilyHouseAnnouncementSpecification {

    private FamilyHouseAnnouncementSpecification() {}
    
    
    public static Specification<FamilyHouseAnnouncement> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<FamilyHouseAnnouncement> hasUserIdOfOwnerInProperty(Long userIdOfOwner) {
        return GenericAnnouncementSpecification.hasUserIdOfOwnerInProperty(userIdOfOwner);
    }

    public static Specification<FamilyHouseAnnouncement> hasIdAndUserIdOfOwnerInProperty(Long id, Long usedIdOfOwner) {
        return GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty(id, usedIdOfOwner);
    }

    public static Specification<FamilyHouseAnnouncement> hasStatuses(List<AnnouncementStatusEnum> statuses) {
        return GenericAnnouncementSpecification.hasStatuses(statuses);
    }

    public static Specification<FamilyHouseAnnouncement> hasStatus(AnnouncementStatusEnum status) {
        return GenericAnnouncementSpecification.hasStatus(status);
    }
    
}
