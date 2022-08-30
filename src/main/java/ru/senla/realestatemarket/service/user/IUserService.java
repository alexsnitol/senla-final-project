package ru.senla.realestatemarket.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IUserService extends UserDetailsService, IAbstractService<User, Long> {

    UserDetails convertUserToUserDetails(User user);

    AuthorizedUser convertUserToAuthorizedUser(User user);

}
