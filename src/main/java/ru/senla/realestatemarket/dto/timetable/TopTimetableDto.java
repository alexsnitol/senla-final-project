package ru.senla.realestatemarket.dto.timetable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TopTimetableDto {

    private Long announcementId;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;

}
