package ru.senla.realestatemarket.controller.property;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.property.HousingPropertyDto;
import ru.senla.realestatemarket.dto.property.PropertyDto;
import ru.senla.realestatemarket.service.property.IHousingPropertyService;
import ru.senla.realestatemarket.service.property.IPropertyService;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final IPropertyService propertyService;
    private final IHousingPropertyService housingPropertyService;


    @GetMapping
    public List<PropertyDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return propertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @GetMapping("/housing")
    public List<HousingPropertyDto> getAllHousingProperties(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return housingPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @GetMapping("/owners/current")
    public List<PropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return propertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @GetMapping("/housing/owners/current")
    public List<HousingPropertyDto> getAllHousingPropertiesDtoOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return housingPropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

}
