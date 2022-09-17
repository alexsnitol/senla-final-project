package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.model.address.Region;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class RegionMapper {
    
    public abstract RegionDto toRegionDto(Region region);
    
    public abstract List<RegionDto> toRegionDto(Collection<Region> regions);
    
    public abstract Region toRegion(RegionDto regionDto);
    
    public abstract List<Region> toRegion(Collection<RegionDto> regionDtos);

    public abstract Region toRegion(RequestRegionDto requestRegionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateRegionFromRequestRegionDto(
            RequestRegionDto regionDto,
            @MappingTarget Region region
    );
    
}
