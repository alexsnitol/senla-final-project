package ru.senla.realestatemarket.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.senla.realestatemarket.mapper.restresponse.RestResponseMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private RestResponseMapper restResponseMapper = Mappers.getMapper(RestResponseMapper.class);


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        restResponseMapper.toRestErrorDto(
                                accessDeniedException.getMessage(),
                                accessDeniedException.getClass().getSimpleName(),
                                HttpServletResponse.SC_FORBIDDEN,
                                request.getRequestURI()
                        )
                )
        );
    }

}
