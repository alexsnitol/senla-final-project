package ru.senla.realestatemarket.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.jwt.JwtRequestDto;
import ru.senla.realestatemarket.dto.jwt.JwtResponseDto;
import ru.senla.realestatemarket.service.user.IAuthService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;


    @PostMapping
    public ResponseEntity<JwtResponseDto> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }

}
