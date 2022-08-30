package ru.senla.realestatemarket.service.address;

import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IRegionService extends IAbstractService<Region, Long> {

    List<RegionDto> getAllDto(String rsqlQuery, String sortQuery);

    void add(RequestRegionDto requestRegionDto);

}
