package ru.senla.realestatemarket.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
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
