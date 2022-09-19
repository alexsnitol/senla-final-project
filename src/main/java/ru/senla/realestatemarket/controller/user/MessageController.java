package ru.senla.realestatemarket.controller.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.user.MessageDto;
import ru.senla.realestatemarket.dto.user.RequestMessageDto;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.service.user.IMessageService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@RestController
public class MessageController {

    private final IMessageService messageService;


    @ApiOperation(
            value = "",
            notes = "Return all users with which current user have been messages" +
                    "in order date of sending last message both incoming and outgoing",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/users")
    public Set<SimplyUserDto> getMessageUsers() {
        return messageService.getAllMessageUsersOfCurrentUser();
    }

    @ApiOperation(
            value = "",
            notes = "Return all messages of current user with user by id in asc order sending date time",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/users/{id}")
    public List<MessageDto> getMessagesOfCurrentUser(@PathVariable Long id) {
        return messageService.getAllMessagesCurrentUserWithUserById(id);
    }

    @ApiOperation(
            value = "",
            notes = "Send a message to user by id from current user",
            authorizations = @Authorization("Authorized user")
    )
    @PostMapping("/users/{id}")
    public ResponseEntity<RestResponseDto> sendMessage(
            @RequestBody @Valid RequestMessageDto message,
            @PathVariable Long id
    ) {
        messageService.sendTextMessageFromCurrentUser(message.getText(), id);

        return new ResponseEntity<>(new RestResponseDto("Message has been sent",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
