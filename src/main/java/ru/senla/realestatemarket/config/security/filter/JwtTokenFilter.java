package ru.senla.realestatemarket.config.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.senla.realestatemarket.exception.InvalidJwtTokenException;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.util.JwtTokenUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final IUserService userService;
    private final HandlerExceptionResolver resolver;


    public JwtTokenFilter(
            JwtTokenUtil jwtTokenUtil,
            IUserService userService,
            @Qualifier(value = "handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        Long userId = null;
        if (jwt != null) {
            try {
                userId = jwtTokenUtil.getUserIdFromToken(jwt);
            } catch (Exception ex) {
                String message = "Token is invalid: " + ex.getMessage();

                log.error(message);
                resolver.resolveException(request, response, null, new InvalidJwtTokenException(message));

                return;
             }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.getByIdWithFetchingRolesAndAuthorities(userId);
            AuthorizedUser authorizedUser = UserUtil.convertUserToAuthorizedUser(user);

            if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwt, authorizedUser))) {
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(
                                authorizedUser,
                                null,
                                authorizedUser.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request, response);
    }
}
