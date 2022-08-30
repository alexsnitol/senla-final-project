package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestFamilyHouseAnnouncementDto {

    @NotNull
    private Double price;

    private String description;

    @NotNull
    private HousingAnnouncementTypeEnum type;

    @NotNull
    private Long familyHousePropertyId;

}
