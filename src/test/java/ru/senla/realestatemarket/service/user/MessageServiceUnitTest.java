package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.user.RequestMessageDto;
import ru.senla.realestatemarket.mapper.user.MessageMapper;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IMessageRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.util.UserUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class MessageServiceUnitTest {

    @Autowired
    IMessageService messageService;
    @Autowired
    IMessageRepository mockedMessageRepository;
    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    MessageMapper mockedMessageMapper;

    Message mockedMessage = mock(Message.class);
    RequestMessageDto mockedRequestMessageDto = mock(RequestMessageDto.class);
    User mockedUser = mock(User.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedMessageMapper,
                mockedMessageRepository,
                mockedUserUtil,
                mockedUserRepository
        );
    }
    

    @Test
    void whenGetAllMessagesCurrentUserWithUserByIdCalled_ThenFindMessagesByUserIdAndReturnUnmappedMessages() {
        // test message list
        List<Message> testMessageList = List.of(mockedMessage);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedMessageRepository.findAllMessagesUserWithUserById(eq(1L), eq(2L), any())).thenReturn(testMessageList);

        messageService.getAllMessagesCurrentUserWithUserById(2L);

        verify(mockedMessageMapper, times(1)).toMessageDto(testMessageList);
    }
    
    @Test
    void whenGetAllMessageUsersOfCurrentUser_ThenFindAllMessageUsersByUserIdSortedByMessageCreatedDateTimeCalledAndReturnSimplyUsersDto() {
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);

        messageService.getAllMessageUsersOfCurrentUser();

        verify(mockedMessageRepository, times(1)).findAllMessageUsersByUserIdSortedByMessageCreatedDateTime(1L);
    }
    
    @Test
    void whenSendMessageCalled_ThenFindBySpecifiedIdAndCreate() {
        // test customer
        User testUserOfSender = mockedUser;

        // test receiver
        User testUserOfReceiver = mock(User.class);


        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfSender);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfReceiver);

        messageService.sendMessage(mockedMessage, 1L, 2L);

        verify(mockedMessage, times(1)).setSender(testUserOfSender);
        verify(mockedMessage, times(1)).setReceiver(testUserOfReceiver);
        verify(mockedMessageRepository, times(1)).create(mockedMessage);
    }

    @Test
    void whenSendMessageFromCurrentUserCalledWithMessageParameter_ThenFindBySpecifiedIdAndCreate() {
        // test sender
        User testUserOfSender = mockedUser;

        // test receiver
        User testUserOfReceiver = mock(User.class);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfSender);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfReceiver);

        messageService.sendMessage(mockedMessage, 1L, 2L);

        verify(mockedMessage, times(1)).setSender(testUserOfSender);
        verify(mockedMessage, times(1)).setReceiver(testUserOfReceiver);
        verify(mockedMessageRepository, times(1)).create(mockedMessage);
    }

    @Test
    void whenSendMessageFromCurrentUserCalledWithRequestMessageDtoParameter_ThenFindBySpecifiedIdAndCreate() {
        // test sender
        User testUserOfSender = mockedUser;

        // test receiver
        User testUserOfReceiver = mock(User.class);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfSender);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfReceiver);
        when(mockedMessageMapper.requestMessageDtoToMessage(mockedRequestMessageDto)).thenReturn(mockedMessage);

        messageService.sendMessage(mockedMessage, 1L, 2L);

        verify(mockedMessage, times(1)).setSender(testUserOfSender);
        verify(mockedMessage, times(1)).setReceiver(testUserOfReceiver);
        verify(mockedMessageRepository, times(1)).create(mockedMessage);
    }

}
