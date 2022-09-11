package ru.senla.realestatemarket.repo.property.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

public class GenericPropertySpecification {

    private GenericPropertySpecification() {}


    public static <M> Specification<M> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static <M> Specification<M> hasUserIdOfOwner(Long userIdOfOwner) {
        return (root, query, criteriaBuilder) -> {
            Join<Property, User> userJoin = root.join("owner");
            return criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner);
        };
    }

    public static <M> Specification<M> hasIdAndUserIdOfOwner(Long id, Long userIdOfOwner) {
        return (root, query, criteriaBuilder) -> {
            Join<Property, User> userJoin = root.join("owner");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("id"), id),
                    criteriaBuilder.equal(userJoin.get("id"), userIdOfOwner)
            );
        };
    }


}
