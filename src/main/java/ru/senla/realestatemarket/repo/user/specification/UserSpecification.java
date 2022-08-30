package ru.senla.realestatemarket.repo.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.User;

public class UserSpecification {

    private UserSpecification() {}


    public static Specification<User> hasUsername(String username) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username));
    }

}
