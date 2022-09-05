package ru.senla.realestatemarket.service.user.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.UserDto;
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

@Slf4j
@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    public UserServiceImpl(IUserRepository userRepository,
                           IRoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

        return UserUtil.convertUserToAuthorizedUser(user);
    }

    @Override
    public User getByIdWithFetchingRolesAndAuthorities(Long id) {
        User user = userRepository.findByIdWithFetchingRolesAndAuthorities(id);
        EntityHelper.checkEntityOnNull(user, clazz, id);

        return user;
    }

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.toUserDto(getById(id));
    }

    @Override
    public List<UserDto> getAllDto(String rsqlQuery, String sortQuery) {
        return userMapper.toUserDto(getAll(rsqlQuery, sortQuery));
    }

    @Override
    public UserDto getDtoOfCurrentUser() {
        User user = getById(UserUtil.getCurrentUserId());

        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void add(RequestUserDto requestUserDto) {
        User user = userMapper.toUser(requestUserDto);

        checkUsernameOnExist(user.getUsername());

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(StandardRoleEnum.USER.name()));

        user.setRoles(roles);


        String password = requestUserDto.getPassword();

        user.setPassword(passwordEncoder.encode(password));


        userRepository.create(user);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestUserDto updateRequestUserDto, Long id) {
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
    public void updateCurrentUser(UpdateRequestUserDto updateRequestUserDto) {
        updateById(updateRequestUserDto, UserUtil.getCurrentUserId());
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

    @Override
    public void deleteCurrentUser() {
        deleteById(UserUtil.getCurrentUserId());
    }

    @Override
    public void blockUserById(Long userId) {
        User user = getById(userId);

        user.setEnabled(false);

        userRepository.update(user);
    }


}
