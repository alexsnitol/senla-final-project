package ru.senla.realestatemarket.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.RoleDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.mapper.user.RoleMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.repo.user.specification.RoleSpecification;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;
import ru.senla.realestatemarket.service.user.IRoleService;
import ru.senla.realestatemarket.service.user.IUserService;

import java.util.List;

import static ru.senla.realestatemarket.repo.user.specification.UserSpecification.hasRole;
import static ru.senla.realestatemarket.repo.user.specification.UserSpecification.isEnabled;

/**
 * @author Alexander Slotin (<a href="https://github.com/alexsnitol">@alexsnitol</a>) <p>
 * 2022 Nov
 */

@Controller
public class GraphQLController {

    private final IHouseMaterialService houseMaterialService;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final RoleMapper roleMapper;


    public GraphQLController(IHouseMaterialService houseMaterialService,
                             IUserService userService,
                             UserMapper userMapper,
                             IRoleService roleService,
                             RoleMapper roleMapper) {
        this.houseMaterialService = houseMaterialService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }


    @QueryMapping
    public List<HouseMaterial> houseMaterials() {
        return houseMaterialService.getAll();
    }

    @QueryMapping
    public List<UserDto> user(@Argument Boolean enabled, @Argument String role) {
        if (enabled != null) {
            if (role != null) {
                return userMapper.toUserDto(userService.getAll(isEnabled(enabled).and(hasRole(role))));
            } else {
                return userMapper.toUserDto(userService.getAll(isEnabled(enabled)));
            }
        } else {
            if (role != null) {
                return userMapper.toUserDto(userService.getAll(hasRole(role)));
            } else {
                return userService.getAllDto(null, null);
            }
        }
    }

    @QueryMapping
    public List<RoleDto> role(@Argument String name) {
        if (name != null) {
            return roleMapper.toRoleDto(roleService.getAll(RoleSpecification.hasName(name)));
        } else {
            return roleMapper.toRoleDto(roleService.getAll());
        }
    }

    @MutationMapping
    public UserDto createUser(
            @Argument String username,
            @Argument String password,
            @Argument String lastName,
            @Argument String firstName,
            @Argument String patronymic,
            @Argument String email,
            @Argument String phoneNumber
    ) {
        RequestUserDto requestUserDto = new RequestUserDto();

        requestUserDto.setUsername(username);
        requestUserDto.setPassword(password);

        if (lastName != null)
            requestUserDto.setLastName(lastName);
        if (firstName != null)
            requestUserDto.setFirstName(firstName);
        if (patronymic != null)
            requestUserDto.setPatronymic(patronymic);
        if (email != null)
            requestUserDto.setEmail(email);
        if (phoneNumber != null)
            requestUserDto.setPhoneNumber(phoneNumber);

        return userService.addFromDto(requestUserDto);
    }

}
