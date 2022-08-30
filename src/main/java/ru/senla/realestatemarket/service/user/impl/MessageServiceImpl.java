package ru.senla.realestatemarket.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.MessageDto;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.mapper.user.MessageMapper;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IMessageRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.user.IMessageService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageServiceImpl extends AbstractServiceImpl<Message, Long> implements IMessageService {

    private final IMessageRepository messageRepository;
    private final IUserRepository userRepository;

    private MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);


    public void init() {
        setDefaultRepository(messageRepository);
        setClazz(Message.class);
    }
    

    private Long getCurrentUserId() {
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return authorizedUser.getId();
    }

    @Override
    public List<MessageDto> getAllMessagesCurrentUserWithUserById(Long userId) {
        List<Message> messageList = messageRepository.findAllMessagesUserWithUserById(
                getCurrentUserId(), userId, Sort.by(Sort.Direction.ASC, "createdDt")
        );

        return messageMapper.toMessageDto(messageList);
    }

    @Override
    public Set<SimplyUserDto> getAllMessageUsersOfCurrentUser() {
        return messageRepository.findAllMessageUsersByUserIdOrderedByMessageCreatedDateTime(getCurrentUserId());
    }

    @Override
    @Transactional
    public void sendMessage(Message message, Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId);
        EntityHelper.checkEntityOnNullAfterFoundById(sender, User.class, senderId);

        User receiver = userRepository.findById(receiverId);
        EntityHelper.checkEntityOnNullAfterFoundById(receiver, User.class, receiverId);

        message.setSender(sender);
        message.setReceiver(receiver);

        messageRepository.create(message);
    }

    @Override
    @Transactional
    public void sendMessageFromCurrentUser(Message message, Long receiverId) {
        sendMessage(message, getCurrentUserId(), receiverId);
    }

    @Override
    @Transactional
    public void sendTextMessageFromCurrentUser(String text, Long receiverId) {
        Message message = new Message();
        message.setText(text);

        sendMessageFromCurrentUser(message, receiverId);
    }

}
