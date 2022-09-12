package ru.senla.realestatemarket.dto.timetable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestRentTimetableWithUserIdOfTenantDto {

    @NotNull
    private LocalDateTime fromDt;

    @NotNull
    private LocalDateTime toDt;

    @NotNull
    private Long userIdOfTenant;

}
