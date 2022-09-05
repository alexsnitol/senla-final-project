package ru.senla.realestatemarket.config;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.senla.realestatemarket.dto.response.RestErrorDto;
import ru.senla.realestatemarket.dto.response.RestValidationErrorDto;
import ru.senla.realestatemarket.exception.BusinessRuntimeException;
import ru.senla.realestatemarket.exception.InvalidJwtTokenException;
import ru.senla.realestatemarket.exception.PropertySpecificOwnerIsDifferentFromRequestedOwnerException;
import ru.senla.realestatemarket.exception.WrongRSQLQueryException;
import ru.senla.realestatemarket.mapper.restresponse.RestResponseMapper;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final RestResponseMapper restResponseMapper = Mappers.getMapper(RestResponseMapper.class);


    private ResponseEntity<Object> handle(Exception ex, WebRequest request, HttpStatus httpStatus) {
        String message = ex.getMessage();
        if (message == null) {
            message = "Message not provided";
        }

        ServletWebRequest servletWebRequest =  (ServletWebRequest) request;
        String uri = servletWebRequest.getRequest().getRequestURI();

        RestErrorDto restErrorDto = restResponseMapper
                .toRestErrorDto(message, ex.getClass().getSimpleName(), httpStatus.value(), uri);

        return handleExceptionInternal(ex, restErrorDto, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(ex, request, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(ex, request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {
            AccessDeniedException.class,
            PropertySpecificOwnerIsDifferentFromRequestedOwnerException.class
    })
    protected ResponseEntity<Object> handleAccessDenied(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthentication(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {InvalidJwtTokenException.class})
    protected ResponseEntity<Object> handleInvalidJwtToken(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, WrongRSQLQueryException.class})
    protected ResponseEntity<Object> handleIllegalArgument(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String message = "Request model validation error";

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ServletWebRequest servletWebRequest =  (ServletWebRequest) request;
        String uri = servletWebRequest.getRequest().getRequestURI();

        RestValidationErrorDto restValidationErrorDto = restResponseMapper
                .toRestValidationErrorDto(
                        message, errors, ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST.value(), uri);

        return handleExceptionInternal(ex, restValidationErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }



    @ExceptionHandler(value = {BusinessRuntimeException.class})
    protected ResponseEntity<Object> handleBusinessRunTime(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntime(RuntimeException ex, WebRequest request) {
        return handle(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleDefault(Exception ex, WebRequest request) {
        return handle(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
