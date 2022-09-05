package ru.senla.realestatemarket.dto.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.user.SimplyUserWithContactsAndRatingDto;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

@Getter
@Setter
public class PropertyDto {

    private Long id;

    private Float area;

    private SimplyUserWithContactsAndRatingDto owner;

    private PropertyStatusEnum status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PropertyTypeEnum propertyType;

}
