package ru.senla.realestatemarket.repo.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

public class UserSpecification {

    private UserSpecification() {}


    public static Specification<User> hasUsername(String username) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username));
    }

    public static Specification<User> notHasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("id"), id);
    }

    public static Specification<User> isEnabled(Boolean enabled) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("enabled"), enabled);
    }

    public static Specification<User> hasRole(String roleName) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Role> roleJoin = root.join("roles");
            return criteriaBuilder.equal(roleJoin.get("name"), roleName);
        };
    }

}
