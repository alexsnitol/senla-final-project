package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;

import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
public class UpdateRequestApartmentAnnouncementDto {

    private Double price;

    private AnnouncementStatusEnum status;

    @Size(min = 1, max = 4095)
    private String description;

    private HousingAnnouncementTypeEnum type;

    private Long apartmentPropertyId;

}
