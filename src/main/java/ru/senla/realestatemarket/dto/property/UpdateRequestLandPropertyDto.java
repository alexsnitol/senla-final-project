package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;


@Getter
@Setter
public class UpdateRequestLandPropertyDto {

    private Float area;

    private Long streetId;

    private PropertyStatusEnum status;

}
