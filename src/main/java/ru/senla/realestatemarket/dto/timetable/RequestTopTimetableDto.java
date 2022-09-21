package ru.senla.realestatemarket.dto.timetable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestTopTimetableDto {

    @NotNull
    private LocalDateTime fromDt;

    @NotNull
    private LocalDateTime toDt;

}
