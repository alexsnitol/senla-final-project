package ru.senla.realestatemarket.dto.user;

import lombok.Data;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
public class SimplyUserDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

}
