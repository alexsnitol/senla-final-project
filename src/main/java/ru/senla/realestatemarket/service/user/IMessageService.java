package ru.senla.realestatemarket.service.user;

import ru.senla.realestatemarket.dto.user.MessageDto;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;
import java.util.Set;

public interface IMessageService extends IAbstractService<Message, Long> {

    List<MessageDto> getAllMessagesCurrentUserWithUserById(Long userId);

    Set<SimplyUserDto> getAllMessageUsersOfCurrentUser();


    Message sendMessage(Message message, Long senderId, Long receiverId);

    Message sendMessageFromCurrentUser(Message message, Long receiverId);
    MessageDto sendTextMessageFromCurrentUser(String text, Long receiverId);

}
