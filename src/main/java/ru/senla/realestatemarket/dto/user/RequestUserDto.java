package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class RequestUserDto {

    @NotBlank
    @Size(max = 255)
    private String username;

    @NotBlank
    @Size(min = 8, max = 1024)
    private String password;

    @Size(min = 1, max = 255)
    private String lastName;

    @Size(min = 1, max = 255)
    private String firstName;

    @Size(min = 1, max = 255)
    private String patronymic;

    @Size(min = 1, max = 255)
    private String email;

    @Size(min = 1, max = 15)
    private String phoneNumber;

}
