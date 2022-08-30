package ru.senla.realestatemarket.repo.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

public class ReviewSpecification {

    private ReviewSpecification() {}


    public static Specification<Review> hasSellerId(Long sellerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, User> sellerJoin = root.join("seller");
            return criteriaBuilder.equal(sellerJoin.get("id"), sellerId);
        };
    }

    public static Specification<Review> hasCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, User> customerJoin = root.join("customer");
            return criteriaBuilder.equal(customerJoin.get("id"), customerId);
        };
    }

}
