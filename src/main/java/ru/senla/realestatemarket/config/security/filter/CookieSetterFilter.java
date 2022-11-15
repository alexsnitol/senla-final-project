package ru.senla.realestatemarket.config.security.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CookieSetterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String authorityUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Cookie cookie = new Cookie("username", authorityUsername);
        cookie.setPath("/");
        response.addCookie(cookie);

        filterChain.doFilter(request, response);
    }

}
