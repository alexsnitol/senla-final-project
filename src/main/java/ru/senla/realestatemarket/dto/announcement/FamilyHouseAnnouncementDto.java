package ru.senla.realestatemarket.dto.announcement;

import lombok.Data;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;

@Data
public class FamilyHouseAnnouncementDto extends HousingAnnouncementDto {

    private FamilyHousePropertyDto property;

}
