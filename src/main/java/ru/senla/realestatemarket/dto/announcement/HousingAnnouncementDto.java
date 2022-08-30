package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;

@Getter
@Setter
public class HousingAnnouncementDto extends AnnouncementDto {

    private HousingAnnouncementTypeEnum type;

}
