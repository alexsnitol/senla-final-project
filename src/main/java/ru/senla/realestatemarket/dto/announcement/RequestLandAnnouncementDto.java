package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class RequestLandAnnouncementDto {

    @NotNull
    private Double price;

    @Size(min = 1, max = 4095)
    private String description;

    @NotNull
    private NonHousingAnnouncementTypeEnum type;

    @NotNull
    private Long landPropertyId;

}
