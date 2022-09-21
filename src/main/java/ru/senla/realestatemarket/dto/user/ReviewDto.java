package ru.senla.realestatemarket.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
public class ReviewDto {

    private Long id;

    private SimplyUserDto customer;

    private SimplyUserWithRatingDto seller;

    private String comment;

    private Short note;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt;

}
