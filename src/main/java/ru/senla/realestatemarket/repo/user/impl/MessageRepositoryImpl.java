package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IMessageRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static ru.senla.realestatemarket.repo.user.specification.MessageSpecification.hasUserIdInSenderIdOrReceiverId;
import static ru.senla.realestatemarket.repo.user.specification.MessageSpecification.hasUsersIdInSenderIdAndReceiverId;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class MessageRepositoryImpl extends AbstractRepositoryImpl<Message, Long> implements IMessageRepository {

    private final UserMapper userMapper;


    public MessageRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @PostConstruct
    public void init() {
        setClazz(Message.class);
    }


    @Override
    public List<Message> findAllMessagesUserWithUserById(Long mainUserId, Long contactUserId, Sort sort) {
        Specification<Message> specification = hasUsersIdInSenderIdAndReceiverId(mainUserId, contactUserId);
        return findAll(specification, sort);
    }

    @Override
    public Set<SimplyUserDto> findAllMessageUsersByUserIdSortedByMessageCreatedDateTime(Long userId) {
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Message> messageRoot = criteriaQuery.from(Message.class);
        Join<Message, User> senderJoin = messageRoot.join("sender");
        Join<Message, User> receiverJoin = messageRoot.join("receiver");

        Predicate predicate = hasUserIdInSenderIdOrReceiverId(userId)
                .toPredicate(messageRoot, criteriaQuery, criteriaBuilder);

        criteriaQuery
                .multiselect(
                        senderJoin.get("id"),
                        senderJoin.get("lastName"),
                        senderJoin.get("firstName"),
                        senderJoin.get("patronymic"),

                        receiverJoin.get("id"),
                        receiverJoin.get("lastName"),
                        receiverJoin.get("firstName"),
                        receiverJoin.get("patronymic"),

                        criteriaBuilder.max(messageRoot.get("createdDt"))
                )
                .distinct(true)
                .orderBy(criteriaBuilder.desc(criteriaBuilder.max(messageRoot.get("createdDt"))))
                .where(predicate)
                .groupBy(
                        senderJoin.get("id"),
                        senderJoin.get("lastName"),
                        senderJoin.get("firstName"),
                        senderJoin.get("patronymic"),

                        receiverJoin.get("id"),
                        receiverJoin.get("lastName"),
                        receiverJoin.get("firstName"),
                        receiverJoin.get("patronymic")
                );


        List<Object[]> objectList = entityManager.createQuery(criteriaQuery).getResultList();

        return convertObjectsToMessageUsersWithoutUserWithUserId(userId, objectList);
    }

    private Set<SimplyUserDto> convertObjectsToMessageUsersWithoutUserWithUserId(
            Long userId, List<Object[]> objectList
    ) {
        Set<SimplyUserDto> messageUserDtoSet = new LinkedHashSet<>();

        for (Object[] obj : objectList) {
            try {
                // check two users in one row
                for (int i = 0; i != 8;) {
                    Long id = (Long) obj[i++];

                    if (id.equals(userId)) {
                        i = i + 3;
                        continue;
                    }

                    String lastName = (String) obj[i++];
                    String firstName = (String) obj[i++];
                    String patronymic = (String) obj[i++];

                    messageUserDtoSet.add(userMapper.toSimplyUserDto(id, lastName, firstName, patronymic));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return messageUserDtoSet;
    }
}
