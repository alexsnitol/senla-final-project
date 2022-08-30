package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.user.SimplyUserDto;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;
import ru.senla.realestatemarket.dto.user.SimplyUserWithRatingDto;
import ru.senla.realestatemarket.model.user.User;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class UserMapper {

    public abstract SimplyUserDto toSimplyUserDto(User user);

    public abstract List<SimplyUserDto> toSimplyUserDto(Collection<User> user);

    public abstract SimplyUserWithRatingDto toSimplyUserWithRatingDto(User user);

    public abstract SimplyUserWithContactsAndRatingDto toSimplyUserWithContactsAndRatingDto(User user);

    public SimplyUserDto toSimplyUserDto(Long id, String lastName, String firstName, String patronymic) {
        SimplyUserDto simplyUserDto = new SimplyUserDto();

        simplyUserDto.setId(id);
        simplyUserDto.setLastName(lastName);
        simplyUserDto.setFirstName(firstName);
        simplyUserDto.setPatronymic(patronymic);

        return simplyUserDto;
    }

}
