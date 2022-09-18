package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.Role;
import ru.senla.realestatemarket.model.user.StandardRoleEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IRoleRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.util.UserUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class UserServiceUnitTest {

    @Autowired
    IUserService userService;
    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    IRoleRepository mockedRoleRepository;
    @Autowired
    PasswordEncoder mockedPasswordEncoder;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    UserMapper mockedUserMapper;

    User mockedUser = mock(User.class);
    UserDto mockedUserDto = mock(UserDto.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedUserRepository,
                mockedRoleRepository,
                mockedUserMapper,
                mockedUserUtil
        );
    }


    @Test
    void whenLoadByUsernameCalled_ThenFindUserAndConvertHisInUserDetailsAndReturn() {
        // test user
        User testUser = mock(User.class);

        // expected user details
        AuthorizedUser expectedUserDetails = mock(AuthorizedUser.class);

        // test
        when(mockedUserRepository.findByUsername("testUser")).thenReturn(testUser);
        when(mockedUserUtil.convertUserToAuthorizedUser(testUser)).thenReturn(expectedUserDetails);

        UserDetails resultUserDetails = userService.loadUserByUsername("testUser");

        assertEquals(expectedUserDetails, resultUserDetails);
    }

    @Test
    void whenLoadByUsernameCalledInCaseOfIfUserNotExist_ThenThrowUsernameNotFoundException() {
        when(mockedUserRepository.findByUsername("notExistUser")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notExistUser"));
    }

    @Test
    void whenGetByIdWithFetchingRolesAndAuthoritiesCalled_ThenFindByIdWithFetchingRolesAndAuthoritiesWithSpecificItCalledAndReturnUser() {
        // test user
        User testUser = mockedUser;

        // expected user
        User expectedUser = mockedUser;

        when(mockedUserRepository.findByIdWithFetchingRolesAndAuthorities(1L)).thenReturn(testUser);

        // test
        User result = userService.getByIdWithFetchingRolesAndAuthorities(1L);

        verify(mockedUserRepository, times(1)).findByIdWithFetchingRolesAndAuthorities(1L);
        assertEquals(expectedUser, result);
    }

    @Test
    void whenGetDtoByIdCalled_ThenFindByIdAndReturnDto() {

        // test user
        User testUser = this.mockedUser;

        // expected dto
        UserDto expectedUserDto = this.mockedUserDto;
        
        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);
        when(mockedUserMapper.toUserDto(testUser)).thenReturn(expectedUserDto);

        UserDto result = userService.getDtoById(1L);

        assertEquals(expectedUserDto, result);
    }

    @Test
    void whenGetAllDtoCalled_ThenFindAllAndReturnDtoList() {

        // test user list
        List<User> testUserList = List.of(this.mockedUser, this.mockedUser);

        // expected dto
        List<UserDto> expectedUserDtoList = List.of(this.mockedUserDto, this.mockedUserDto);


        // test
        when(mockedUserRepository.findAllByQuery(null, null)).thenReturn(testUserList);
        when(mockedUserMapper.toUserDto(testUserList)).thenReturn(expectedUserDtoList);

        List<UserDto> result = userService.getAllDto(null, null);

        assertEquals(expectedUserDtoList, result);
    }

    @Test
    void whenAddFromDtoCalled_ThenCreateWithUnmappedUserCalled() {
        // test dto
        RequestUserDto testRequestUserDto = mock(RequestUserDto.class);

        // test role
        Role testRole = new Role();
        testRole.setName("USER");

        List<Role> testRoleList = List.of(testRole);

        // test encoded password
        String testEncodedPassword = "encodedPassword";

        // test user
        User testUser = mockedUser;


        // test
        when(mockedUserMapper.toUser(testRequestUserDto)).thenReturn(testUser);
        when(mockedRoleRepository.findByName(StandardRoleEnum.USER.name())).thenReturn(testRole);
        when(testUser.getUsername()).thenReturn("username");
        when(testRequestUserDto.getPassword()).thenReturn("password");
        when(mockedPasswordEncoder.encode("password")).thenReturn(testEncodedPassword);

        userService.addFromDto(testRequestUserDto);

        verify(testUser, times(1)).setRoles(testRoleList);
        verify(testUser, times(1)).setPassword(testEncodedPassword);
        verify(mockedUserRepository, times(1)).findByUsername("username");
        verify(mockedUserRepository, times(1)).create(testUser);
    }

    @Test
    void whenUpdateByIdFromDtoCalled_ThenFindByIdFindByUsernameAndUpdateFromDtoAndUpdateCalled() {
        // test user
        User testUser = mockedUser;


        // test update request dto
        UpdateRequestUserDto testUpdateRequestUserDto = mock(UpdateRequestUserDto.class);

        // test password
        String testPassword = "password";

        // test encoded password
        String testEncodedPassword = "encodedPassword";


        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);
        when(mockedPasswordEncoder.encode(testPassword)).thenReturn(testEncodedPassword);
        when(testUpdateRequestUserDto.getPassword()).thenReturn(testPassword);

        userService.updateByIdFromDto(testUpdateRequestUserDto, 1L);

        verify(testUpdateRequestUserDto, times(1)).setPassword(testEncodedPassword);
        verify(mockedUserMapper, times(1)).updateUserFromRequestUserDto(testUpdateRequestUserDto, testUser);
        verify(mockedUserRepository, times(1)).update(testUser);
    }

    @Test
    void whenUpdateCurrentUserFromDto_ThenFindCurrentUserAndUpdate() {
        // test user
        User testUser = mockedUser;


        // test update request dto
        UpdateRequestUserDto testUpdateRequestUserDto = mock(UpdateRequestUserDto.class);

        // test password
        String testPassword = "password";

        // test encoded password
        String testEncodedPassword = "encodedPassword";


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);
        when(mockedPasswordEncoder.encode(testPassword)).thenReturn(testEncodedPassword);
        when(testUpdateRequestUserDto.getPassword()).thenReturn(testPassword);

        userService.updateByIdFromDto(testUpdateRequestUserDto, 1L);

        verify(testUpdateRequestUserDto, times(1)).setPassword(testEncodedPassword);
        verify(mockedUserMapper, times(1)).updateUserFromRequestUserDto(testUpdateRequestUserDto, testUser);
        verify(mockedUserRepository, times(1)).update(testUser);
    }

    @Test
    void whenUserWithItUsernameIsExistCalledInCaseUserIsExist_ThenFindBySpecificUsernameReturnTrue() {
        // test username
        String testUsername = "existUsername";

        // test
        when(mockedUserRepository.findByUsername(testUsername)).thenReturn(mockedUser);

        Boolean result = userService.userWithItUsernameIsExist(testUsername);

        assertTrue(result);
    }

    @Test
    void whenUserWithItUsernameIsExistCalledInCaseUserIsNotExist_ThenFindBySpecificUsernameReturnTrue() {
        // test username
        String testUsername = "notExistUsername";

        // test
        when(mockedUserRepository.findByUsername(testUsername)).thenReturn(null);

        Boolean result = userService.userWithItUsernameIsExist(testUsername);

        assertFalse(result);
    }

}
