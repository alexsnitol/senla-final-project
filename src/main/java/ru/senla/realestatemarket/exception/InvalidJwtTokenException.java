package ru.senla.realestatemarket.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {

    public InvalidJwtTokenException() {
        super("Invalid JWT token");
    }

    public InvalidJwtTokenException(String message) {
        super(message);
    }

}
