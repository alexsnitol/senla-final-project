package ru.senla.realestatemarket.dto.announcement;

import lombok.Data;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

@Data
public class LandAnnouncementDto extends AnnouncementDto {

    private NonHousingAnnouncementTypeEnum type;

    private LandPropertyDto property;

}
