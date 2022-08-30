package ru.senla.realestatemarket.service.user.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IUserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;


    @PostConstruct
    public void init() {
        setDefaultRepository(userRepository);
        setClazz(User.class);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            String message = String.format("User with username %s not found", username);
            log.error(message);
            throw new UsernameNotFoundException(message);
        }

        return convertUserToAuthorizedUser(user);
    }

    @Override
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

    private Collection<? extends GrantedAuthority> getGrantedAuthoritiesByUser(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Collection<Role> roles = user.getRoles();

        // Adding all roles of user
        authorities.addAll(roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList()));

        // Adding all authorities of roles
        roles.forEach(r -> r.getAuthorities()
                .forEach(a -> authorities.add(new SimpleGrantedAuthority(a.getName()))));

        return authorities;
    }


}
