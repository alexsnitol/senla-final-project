package ru.senla.realestatemarket.repo.announcement.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class ApartmentAnnouncementSpecification {
    
    private ApartmentAnnouncementSpecification() {}
    
    
    public static Specification<ApartmentAnnouncement> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<ApartmentAnnouncement> hasUserIdOfOwnerInProperty(Long userIdOfOwner) {
        return GenericAnnouncementSpecification.hasUserIdOfOwnerInProperty(userIdOfOwner);
    }

    public static Specification<ApartmentAnnouncement> hasIdAndUserIdOfOwnerInProperty(Long id, Long usedIdOfOwner) {
        return GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty(id, usedIdOfOwner);
    }

    public static Specification<ApartmentAnnouncement> hasStatuses(List<AnnouncementStatusEnum> statuses) {
        return GenericAnnouncementSpecification.hasStatuses(statuses);
    }

    public static Specification<ApartmentAnnouncement> hasStatus(AnnouncementStatusEnum status) {
        return GenericAnnouncementSpecification.hasStatus(status);
    }
    
}
