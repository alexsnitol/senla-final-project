package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.senla.realestatemarket.dto.jwt.JwtRequestDto;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.service.user.impl.AuthServiceImpl;
import ru.senla.realestatemarket.util.JwtTokenUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AuthServiceUnitTest {

    AuthServiceImpl authService;
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("examplesupersecurekeyforencodinganddecodingtheHS256algorithm");
    @Mock IUserService mockedUserService;

    @BeforeEach
    public void init() {
        this.authService = new AuthServiceImpl(mockedUserService, jwtTokenUtil);
    }

    @Test
    void whenCreateAuthTokenCalled_thenAuthenticateUserAndReturnJwtResponseDto() {
        // test authRequest
        JwtRequestDto authRequest = new JwtRequestDto("testUser", "testPassword");

        // test userDetails
        AuthorizedUser testAuthorizedUser = new AuthorizedUser(
                1L,
                "testUser",
                "testPassword",
                true,
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("testRole"), new SimpleGrantedAuthority("testAuthority"))
        );

        // expected jwt response
        String expectedJwtResponseToken = jwtTokenUtil.generateToken(testAuthorizedUser);

        // test
        when(mockedUserService.loadUserByUsername("testUser")).thenReturn(testAuthorizedUser);

        String result = authService.createAuthToken(authRequest).getToken();

        assertEquals(jwtTokenUtil.getUserIdFromToken(expectedJwtResponseToken), jwtTokenUtil.getUserIdFromToken(result));
        assertEquals(jwtTokenUtil.getUsernameFromToken(expectedJwtResponseToken), jwtTokenUtil.getUsernameFromToken(result));
    }


}
