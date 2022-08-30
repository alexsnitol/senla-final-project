package ru.senla.realestatemarket.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.senla.realestatemarket.mapper.restresponse.RestResponseMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private RestResponseMapper restResponseMapper = Mappers.getMapper(RestResponseMapper.class);


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        restResponseMapper.toRestErrorDto(
                                authException.getMessage(),
                                authException.getClass().getSimpleName(),
                                HttpServletResponse.SC_UNAUTHORIZED,
                                request.getRequestURI()
                        )
                )
        );
    }

}
