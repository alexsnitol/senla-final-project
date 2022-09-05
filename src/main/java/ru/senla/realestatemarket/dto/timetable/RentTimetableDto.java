package ru.senla.realestatemarket.dto.timetable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentTimetableDto {

    private Long announcementId;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;

}
