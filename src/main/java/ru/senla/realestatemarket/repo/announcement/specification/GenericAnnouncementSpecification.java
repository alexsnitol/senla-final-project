package ru.senla.realestatemarket.repo.announcement.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;
import java.util.List;

public class GenericAnnouncementSpecification {

    private GenericAnnouncementSpecification() {}


    public static <T> Specification<T> hasUserIdOfOwnerInProperty(Long userIdOfOwner) {
        return (root, query, criteriaBuilder) -> {
            Join<T, Property> propertyJoin = root.join("property");
            Join<Property, User> userJoin = propertyJoin.join("owner");

            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static <T> Specification<T> hasStatuses(List<AnnouncementStatusEnum> statuses) {
        return (root, query, criteriaBuilder) -> root.get("status").in(statuses);
    }

    public static <T> Specification<T> hasStatus(AnnouncementStatusEnum status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

}
