package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestApartmentPropertyDto {

    private Float area;

    private Short numberOfRooms;

    private Long renovationTypeId;

    @Size(min = 1, max = 255)
    private String apartmentNumber;

    private Short floor;

    @NotNull
    private Long apartmentHouseId;

}
