package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestLandAnnouncementDto {

    @NotNull
    private Double price;

    private String description;

    @NotNull
    private NonHousingAnnouncementTypeEnum type;

    @NotNull
    private Long landPropertyId;

}
