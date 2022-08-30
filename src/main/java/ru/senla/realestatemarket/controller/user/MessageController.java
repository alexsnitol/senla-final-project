package ru.senla.realestatemarket.controller.user;

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


    @GetMapping("/users")
    public Set<SimplyUserDto> getMessageUsers() {
        return messageService.getAllMessageUsersOfCurrentUser();
    }

    @GetMapping("/users/{id}")
    public List<MessageDto> getMessagesOfCurrentUser(@PathVariable Long id) {
        return messageService.getAllMessagesCurrentUserWithUserById(id);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<RestResponseDto> sendMessage(
            @RequestBody @Valid RequestMessageDto message,
            @PathVariable Long id
    ) {
        messageService.sendTextMessageFromCurrentUser(message.getText(), id);

        return ResponseEntity.ok(new RestResponseDto("Message successful sent", HttpStatus.OK.value()));
    }

}
