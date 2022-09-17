package ru.senla.realestatemarket.dto.property;

import lombok.Data;

@Data
public class HousingPropertyDto extends PropertyDto {

    private Short numberOfRooms;

    private String renovationType;

}
