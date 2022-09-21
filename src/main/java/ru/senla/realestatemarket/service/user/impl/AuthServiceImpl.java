package ru.senla.realestatemarket.service.user.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.jwt.JwtRequestDto;
import ru.senla.realestatemarket.dto.jwt.JwtResponseDto;
import ru.senla.realestatemarket.service.user.IAuthService;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.util.JwtTokenUtil;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    public AuthServiceImpl(IUserService userService,
                           JwtTokenUtil jwtTokenUtil,
                           AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    private void authenticate(@NonNull String username, @NonNull String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public JwtResponseDto createAuthToken(@NonNull JwtRequestDto authRequest) {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException ex) {
            String message = "Incorrect username or password";
            log.error(message);
            throw new AuthorizationServiceException(message);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponseDto(token);
    }
}
