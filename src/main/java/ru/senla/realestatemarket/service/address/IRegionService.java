package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IRegionService extends IAbstractService<Region, Long> {

    RegionDto getDtoById(Long id);

    List<RegionDto> getAllDto(String rsqlQuery, String sortQuery);

    RegionDto add(RequestRegionDto requestRegionDto);

    void updateById(RequestRegionDto requestRegionDto, Long id);

}
