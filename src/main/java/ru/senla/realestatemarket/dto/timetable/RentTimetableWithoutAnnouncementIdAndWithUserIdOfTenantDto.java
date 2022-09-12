package ru.senla.realestatemarket.dto.timetable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto {

    private SimplyUserWithContactsAndRatingDto tenant;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime fromDt;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime toDt;

}
