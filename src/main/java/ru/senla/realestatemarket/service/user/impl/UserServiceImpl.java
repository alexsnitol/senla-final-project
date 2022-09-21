package ru.senla.realestatemarket.service.user.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.exception.BusinessRuntimeException;
import ru.senla.realestatemarket.exception.UsernameAlreadyExistsException;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.model.user.StandardRoleEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IRoleRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;


    public UserServiceImpl(IUserRepository userRepository,
                           IRoleRepository roleRepository,
                           UserUtil userUtil,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userUtil = userUtil;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

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

        return userUtil.convertUserToAuthorizedUser(user);
    }

    @Override
    public User getByIdWithFetchingRolesAndAuthorities(Long id) {
        User user = userRepository.findByIdWithFetchingRolesAndAuthorities(id);
        EntityHelper.checkEntityOnNull(user, clazz, id);

        return user;
    }

    @Override
    @Transactional
    public UserDto getDtoById(Long id) {
        return userMapper.toUserDto(getById(id));
    }

    @Override
    @Transactional
    public List<UserDto> getAllDto(String rsqlQuery, String sortQuery) {
        return userMapper.toUserDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public UserDto getDtoOfCurrentUser() {
        User user = getById(userUtil.getCurrentUserId());

        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void addFromDto(RequestUserDto requestUserDto) {
        User user = userMapper.toUser(requestUserDto);

        checkUsernameOnExist(user.getUsername());

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName(StandardRoleEnum.ROLE_USER.name());
        if (userRole == null) {
            String message = "Role with name USER not found. Adding new users impossible.";

            log.error(message);
            throw new BusinessRuntimeException(message);
        }

        roles.add(userRole);
        user.setRoles(roles);


        String password = requestUserDto.getPassword();

        user.setPassword(passwordEncoder.encode(password));


        userRepository.create(user);
    }

    @Override
    @Transactional
    public void updateByIdFromDto(UpdateRequestUserDto updateRequestUserDto, Long id) {
        User user = getById(id);

        checkUsernameOnExistExcludingId(updateRequestUserDto.getUsername(), id);

        String newPassword = updateRequestUserDto.getPassword();
        if (newPassword != null) {
            updateRequestUserDto.setPassword(passwordEncoder.encode(newPassword));
        }

        userMapper.updateUserFromRequestUserDto(updateRequestUserDto, user);

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void updateCurrentUserFromDto(UpdateRequestUserDto updateRequestUserDto) {
        updateByIdFromDto(updateRequestUserDto, userUtil.getCurrentUserId());
    }

    /**
     * @throws UsernameAlreadyExistsException if username already exists
     */
    private void checkUsernameOnExistExcludingId(String username, Long excludingUserId) {
        if (Boolean.TRUE.equals(userWithItUsernameIsExistExcludingId(username, excludingUserId))) {
            String message = String.format("Username %s already exists", username);

            log.error(message);
            throw new UsernameAlreadyExistsException(message);
        }
    }

    private Boolean userWithItUsernameIsExistExcludingId(String username, Long excludingUserId) {
        User user = userRepository.findByUsernameExcludingId(username, excludingUserId);

        return user != null;
    }

    /**
     * @throws UsernameAlreadyExistsException if username already exists
     */
    private void checkUsernameOnExist(String username) {
        if (Boolean.TRUE.equals(userWithItUsernameIsExist(username))) {
            String message = String.format("Username %s already exists", username);

            log.error(message);
            throw new UsernameAlreadyExistsException(message);
        }
    }

    @Override
    public Boolean userWithItUsernameIsExist(String username) {
        User user = userRepository.findByUsername(username);

        return user != null;
    }

}
