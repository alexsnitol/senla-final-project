package ru.senla.realestatemarket.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.model.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Component
public class UserUtil {

    public Long getCurrentUserId() {
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return authorizedUser.getId();
    }

    public UserDetails convertUserToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getGrantedAuthoritiesByUser(user)
        );
    }

    public AuthorizedUser convertUserToAuthorizedUser(User user) {
        return new AuthorizedUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getGrantedAuthoritiesByUser(user)
        );
    }

    public Collection<GrantedAuthority> getGrantedAuthoritiesByUser(User user) {
        Collection<Role> roles = user.getRoles();

        // Adding all roles of user
        List<GrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());

        // Adding all authorities of roles
        roles.forEach(r -> r.getAuthorities()
                .forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getName()))));

        return authorities;
    }

}
