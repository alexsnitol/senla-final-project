package ru.senla.realestatemarket.repo.announcement.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class LandAnnouncementSpecification {

    private LandAnnouncementSpecification() {}
    
    
    public static Specification<LandAnnouncement> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<LandAnnouncement> hasUserIdOfOwnerInProperty(Long userIdOfOwner) {
        return GenericAnnouncementSpecification.hasUserIdOfOwnerInProperty(userIdOfOwner);
    }

    public static Specification<LandAnnouncement> hasIdAndUserIdOfOwnerInProperty(Long id, Long usedIdOfOwner) {
        return GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty(id, usedIdOfOwner);
    }

    public static Specification<LandAnnouncement> hasStatuses(List<AnnouncementStatusEnum> statuses) {
        return GenericAnnouncementSpecification.hasStatuses(statuses);
    }

    public static Specification<LandAnnouncement> hasStatus(AnnouncementStatusEnum status) {
        return GenericAnnouncementSpecification.hasStatus(status);
    }
    
}
