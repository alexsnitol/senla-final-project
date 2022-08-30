package ru.senla.realestatemarket.mapper.address;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.model.address.Region;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class RegionMapper {
    
    public abstract RegionDto toRegionDto(Region region);
    
    public abstract List<RegionDto> toRegionDto(Collection<Region> regions);
    
    public abstract Region toRegion(RegionDto regionDto);
    
    public abstract List<Region> toRegion(Collection<RegionDto> regionDtos);

    public abstract Region toRegion(RequestRegionDto requestRegionDto);
    
}
