package ru.senla.realestatemarket.dto.user;

import lombok.Data;

@Data
public class SimplyUserWithRatingDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private Float rating;

}
