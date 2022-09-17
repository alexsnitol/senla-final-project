package ru.senla.realestatemarket.controller.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.service.user.IUserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final IUserService userService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return userService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestUserDto requestUserDto
    ) {
        userService.addFromDto(requestUserDto);

        return new ResponseEntity<>(new RestResponseDto("User has been registered",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{id}")
    public UserDto getById(
            @PathVariable Long id
    ) {
        return userService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        userService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("User has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestUserDto updateRequestUserDto
    ) {
        userService.updateByIdFromDto(updateRequestUserDto, id);

        return ResponseEntity.ok(new RestResponseDto("User has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userService.getDtoOfCurrentUser();
    }

    @PutMapping("/current")
    public ResponseEntity<RestResponseDto> updateCurrentUser(
            @RequestBody @Valid UpdateRequestUserDto updateRequestUserDto
    ) {
        userService.updateCurrentUserFromDto(updateRequestUserDto);

        return ResponseEntity.ok(new RestResponseDto("Current user has been updated", HttpStatus.OK.value()));
    }

}

