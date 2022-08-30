package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestFamilyHousePropertyDto {

    private Float area;

    private Short numberOfRooms;

    private Long renovationTypeId;

    @NotNull
    private Long familyHouseId;

}
