package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HousingPropertyDto extends PropertyDto {

    private Short numberOfRooms;

    private String renovationType;

}
