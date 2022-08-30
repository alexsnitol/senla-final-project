package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplyUserWithRatingDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private Float rating;

}
