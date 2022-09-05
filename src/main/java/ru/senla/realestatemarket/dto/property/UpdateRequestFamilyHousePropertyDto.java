package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;

@Getter
@Setter
public class UpdateRequestFamilyHousePropertyDto {

    private Float area;

    private Short numberOfRooms;

    private Long renovationTypeId;

    private Long familyHouseId;

    private PropertyStatusEnum status;

}
