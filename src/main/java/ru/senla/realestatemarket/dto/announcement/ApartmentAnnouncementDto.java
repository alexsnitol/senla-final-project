package ru.senla.realestatemarket.dto.announcement;

import lombok.Data;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;

@Data
public class ApartmentAnnouncementDto extends HousingAnnouncementDto {

    private ApartmentPropertyDto property;

}
