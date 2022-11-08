package ru.senla.realestatemarket.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IUserService extends UserDetailsService, IAbstractService<User, Long> {

    User getByIdWithFetchingRolesAndAuthorities(Long id);

    UserDto getDtoById(Long id);
    List<UserDto> getAllDto(String rsqlQuery, String sortQuery);
    UserDto getDtoOfCurrentUser();

    UserDto addFromDto(RequestUserDto requestUserDto);

    void updateByIdFromDto(UpdateRequestUserDto updateRequestUserDto, Long id);
    void updateCurrentUserFromDto(UpdateRequestUserDto updateRequestUserDto);

    Boolean userWithItUsernameIsExist(String username);

}
