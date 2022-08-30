package ru.senla.realestatemarket.repo.user;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;
import java.util.Set;

public interface IMessageRepository extends IAbstractRepository<Message, Long> {

    List<Message> findAllMessagesUserWithUserById(Long mainUserId, Long contactUserId, Sort sort);

    Set<SimplyUserDto> findAllMessageUsersByUserIdOrderedByMessageCreatedDateTime(Long userId);

}
