package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;

@Getter
@Setter
public class LandAnnouncementDto extends AnnouncementDto {

    private NonHousingAnnouncementTypeEnum type;

    private LandPropertyDto property;

}
