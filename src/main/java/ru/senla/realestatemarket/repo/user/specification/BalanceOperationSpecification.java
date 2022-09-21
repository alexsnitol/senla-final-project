package ru.senla.realestatemarket.repo.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class BalanceOperationSpecification {

    private BalanceOperationSpecification() {}


    public static Specification<BalanceOperation> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            Join<BalanceOperation, User> userJoin = root.join("user");
            return criteriaBuilder.equal(userJoin.get("id"), userId);
        };
    }

}
