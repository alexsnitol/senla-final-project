package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplyUserWithContactsAndRatingDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String email;

    private String phoneNumber;

    private Float rating;

}
