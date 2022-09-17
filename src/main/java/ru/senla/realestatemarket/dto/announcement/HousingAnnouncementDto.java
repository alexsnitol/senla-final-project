package ru.senla.realestatemarket.dto.announcement;

import lombok.Data;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;

@Data
public class HousingAnnouncementDto extends AnnouncementDto {

    private HousingAnnouncementTypeEnum type;

}
