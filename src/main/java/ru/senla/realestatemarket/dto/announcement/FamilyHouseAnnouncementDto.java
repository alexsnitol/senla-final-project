package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;

@Getter
@Setter
public class FamilyHouseAnnouncementDto extends HousingAnnouncementDto {

    private FamilyHousePropertyDto property;

}
