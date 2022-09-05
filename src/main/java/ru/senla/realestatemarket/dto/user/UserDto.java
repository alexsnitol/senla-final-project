package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String username;

    private Boolean enabled;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String email;

    private String phoneNumber;

    private Double balance;

    private Float rating;

    private List<RoleDto> roles;

}
