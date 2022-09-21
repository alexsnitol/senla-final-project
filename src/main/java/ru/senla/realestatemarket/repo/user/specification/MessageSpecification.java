package ru.senla.realestatemarket.repo.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.model.user.User;

import javax.persistence.criteria.Join;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class MessageSpecification {

    private MessageSpecification() {}


    public static Specification<Message> hasUserIdInSenderIdOrReceiverId(Long userId) {
        return ((root, query, criteriaBuilder) -> {
            Join<Message, User> senderJoin = root.join("sender");
            Join<Message, User> receiverJoin = root.join("receiver");
            return criteriaBuilder.or(
                    criteriaBuilder.equal(senderJoin.get("id"), userId),
                    criteriaBuilder.equal(receiverJoin.get("id"), userId)
            );
        });
    }

    public static Specification<Message> hasUsersIdInSenderIdAndReceiverId(Long mainUserId, Long contactUserId) {
        return ((root, query, criteriaBuilder) -> {
            Join<Message, User> senderJoin = root.join("sender");
            Join<Message, User> receiverJoin = root.join("receiver");
            return criteriaBuilder.and(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(senderJoin.get("id"), mainUserId),
                            criteriaBuilder.equal(senderJoin.get("id"), contactUserId)
                    ),
                    criteriaBuilder.or(
                            criteriaBuilder.equal(receiverJoin.get("id"), mainUserId),
                            criteriaBuilder.equal(receiverJoin.get("id"), contactUserId)
                    )
            );
        });
    }

}
