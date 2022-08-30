package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.model.property.RenovationType;

@Mapper
public abstract class RenovationTypeMapper {

    public String renovationTypeToString(RenovationType renovationType) {
        return renovationType.getName();
    }

    public abstract RenovationType toRenovationType(RequestRenovationTypeDto requestRenovationTypeDto);

}
