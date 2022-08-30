package ru.senla.realestatemarket.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;

@Getter
@Setter
public class ApartmentAnnouncementDto extends HousingAnnouncementDto {

    private ApartmentPropertyDto property;

}
