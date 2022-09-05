package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.dto.user.UpdateRequestUserDto;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;
import ru.senla.realestatemarket.dto.user.SimplyUserWithRatingDto;
import ru.senla.realestatemarket.dto.user.UserDto;
import ru.senla.realestatemarket.model.user.User;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {RoleMapper.class})
public abstract class UserMapper {

    public abstract SimplyUserDto toSimplyUserDto(User user);

    public abstract List<SimplyUserDto> toSimplyUserDto(Collection<User> user);

    public abstract SimplyUserWithRatingDto toSimplyUserWithRatingDto(User user);

    public abstract SimplyUserWithContactsAndRatingDto toSimplyUserWithContactsAndRatingDto(User user);

    public abstract List<SimplyUserWithContactsAndRatingDto> toSimplyUserWithContactsAndRatingDto(
            Collection<User> users);

    public SimplyUserDto toSimplyUserDto(Long id, String lastName, String firstName, String patronymic) {
        SimplyUserDto simplyUserDto = new SimplyUserDto();

        simplyUserDto.setId(id);
        simplyUserDto.setLastName(lastName);
        simplyUserDto.setFirstName(firstName);
        simplyUserDto.setPatronymic(patronymic);

        return simplyUserDto;
    }

    public abstract User toUser(RequestUserDto requestUserDto);

    public abstract UserDto toUserDto(User user);
    public abstract List<UserDto> toUserDto(Collection<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserFromRequestUserDto(
            UpdateRequestUserDto updateRequestUserDto,
            @MappingTarget User user
    );

}
