package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestReviewDto {

    @Size(min = 1, max = 1023)
    @NotBlank
    private String comment;

    private Short note;

}
