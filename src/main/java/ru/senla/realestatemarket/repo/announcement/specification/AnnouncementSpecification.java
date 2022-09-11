package ru.senla.realestatemarket.repo.announcement.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.repo.specification.GenericSpecification;

import java.util.List;

public class AnnouncementSpecification {
    
    private AnnouncementSpecification() {}
    
    
    public static Specification<Announcement> hasId(Long id) {
        return GenericSpecification.hasId(id);
    }

    public static Specification<Announcement> hasUserIdOfOwnerInProperty(Long userIdOfOwner) {
        return GenericAnnouncementSpecification.hasUserIdOfOwnerInProperty(userIdOfOwner);
    }

    public static Specification<Announcement> hasStatuses(List<AnnouncementStatusEnum> statuses) {
        return GenericAnnouncementSpecification.hasStatuses(statuses);
    }

    public static Specification<Announcement> hasStatus(AnnouncementStatusEnum status) {
        return GenericAnnouncementSpecification.hasStatus(status);
    }
    
}
