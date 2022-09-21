package ru.senla.realestatemarket.dto.timetable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;

import java.time.LocalDateTime;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
public class RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto {

    private SimplyUserWithContactsAndRatingDto tenant;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime fromDt;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime toDt;

}
