package ru.senla.realestatemarket.mapper.property;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.model.property.RenovationType;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class RenovationTypeMapper {

    public String renovationTypeToString(RenovationType renovationType) {
        return renovationType.getName();
    }

    public abstract RenovationType toRenovationType(RequestRenovationTypeDto requestRenovationTypeDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateRenovationTypeFromRequestRenovationTypeDto(
            RequestRenovationTypeDto requestRenovationTypeDto,
            @MappingTarget RenovationType renovationType
    );

}
