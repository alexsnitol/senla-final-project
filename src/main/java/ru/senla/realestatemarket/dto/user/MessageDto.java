package ru.senla.realestatemarket.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MessageDto {

    private Long id;

    private SimplyUserDto sender;

    private SimplyUserDto receiver;

    private String text;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdDate;

    @JsonFormat(pattern = "hh:mm:ss")
    private LocalTime createdTime;

}
