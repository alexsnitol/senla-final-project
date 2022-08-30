package ru.senla.realestatemarket.config.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.util.JwtTokenUtil;

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
    private final IUserRepository userRepository;


    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
                          IUserService userService,
                          IUserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userRepository = userRepository;
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
             }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userRepository.findById(userId);
            AuthorizedUser authorizedUser = userService.convertUserToAuthorizedUser(user);

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
