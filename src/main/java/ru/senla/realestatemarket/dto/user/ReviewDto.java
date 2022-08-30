package ru.senla.realestatemarket.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private SimplyUserDto customer;

    private SimplyUserWithRatingDto seller;

    private String comment;

    private Short note;

    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime createdDt;

}
