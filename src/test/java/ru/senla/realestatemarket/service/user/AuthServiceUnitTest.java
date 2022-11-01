package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.jwt.JwtRequestDto;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.util.JwtTokenUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class AuthServiceUnitTest {

    @Autowired
    IAuthService authService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    IUserService mockedUserService;
    @Autowired
    AuthenticationManager mockedAuthenticationManager;

    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedUserService,
                mockedAuthenticationManager
        );
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
