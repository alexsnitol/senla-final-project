package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequestApartmentPropertyDto {

    private Float area;

    private Short numberOfRooms;

    private Long renovationTypeId;

    @Size(min = 1, max = 255)
    private String apartmentNumber;

    private Short floor;

    private Long apartmentHouseId;

    private PropertyStatusEnum status;

}
