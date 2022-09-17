package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.MessageDto;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.mapper.user.MessageMapper;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IMessageRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.user.IMessageService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MessageServiceImpl extends AbstractServiceImpl<Message, Long> implements IMessageService {

    private final IMessageRepository messageRepository;
    private final IUserRepository userRepository;
    private final UserUtil userUtil;

    private final MessageMapper messageMapper;

    public MessageServiceImpl(IMessageRepository messageRepository,
                              IUserRepository userRepository,
                              UserUtil userUtil,
                              MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.messageMapper = messageMapper;
    }


    public void init() {
        setDefaultRepository(messageRepository);
        setClazz(Message.class);
    }


    @Override
    public List<MessageDto> getAllMessagesCurrentUserWithUserById(Long userId) {
        List<Message> messageList = messageRepository.findAllMessagesUserWithUserById(
                userUtil.getCurrentUserId(), userId, Sort.by(Sort.Direction.ASC, "createdDt")
        );

        return messageMapper.toMessageDto(messageList);
    }

    @Override
    public Set<SimplyUserDto> getAllMessageUsersOfCurrentUser() {
        return messageRepository.findAllMessageUsersByUserIdSortedByMessageCreatedDateTime(userUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public void sendMessage(Message message, Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId);
        EntityHelper.checkEntityOnNull(sender, User.class, senderId);

        User receiver = userRepository.findById(receiverId);
        EntityHelper.checkEntityOnNull(receiver, User.class, receiverId);

        message.setSender(sender);
        message.setReceiver(receiver);

        messageRepository.create(message);
    }

    @Override
    @Transactional
    public void sendMessageFromCurrentUser(Message message, Long receiverId) {
        sendMessage(message, userUtil.getCurrentUserId(), receiverId);
    }

    @Override
    @Transactional
    public void sendTextMessageFromCurrentUser(String text, Long receiverId) {
        Message message = new Message();
        message.setText(text);

        sendMessageFromCurrentUser(message, receiverId);
    }

}
