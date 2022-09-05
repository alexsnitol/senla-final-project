package ru.senla.realestatemarket.controller.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.address.CityDto;
import ru.senla.realestatemarket.dto.address.RequestCityDto;
import ru.senla.realestatemarket.dto.address.RequestCityWithoutRegionIdDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestCityDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.ICityService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CityController {

    private final ICityService cityService;


    @GetMapping("/cities/{id}")
    public CityDto getById(
            @PathVariable Long id
    ) {
        return cityService.getDtoById(id);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<RestResponseDto> deletedById(
            @PathVariable Long id
    ) {
        cityService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("City has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestCityDto updateRequestCityDto
    ) {
        cityService.updateById(updateRequestCityDto, id);

        return ResponseEntity.ok(new RestResponseDto("City has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/cities")
    public List<CityDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return cityService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping("/cities")
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestCityDto requestCityDto
    ) {
        cityService.add(requestCityDto);

        return new ResponseEntity<>(new RestResponseDto("City has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}")
    public CityDto getByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId
    ) {
        return cityService.getDtoRegionIdAndByCityId(regionId, cityId);
    }

    @DeleteMapping("/regions/{regionId}/cities/{cityId}")
    public ResponseEntity<RestResponseDto> deleteByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId
    ) {
        cityService.deleteRegionIdAndCityById(regionId, cityId);

        return ResponseEntity.ok(new RestResponseDto("City has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/regions/{regionId}/cities/{cityId}")
    public ResponseEntity<RestResponseDto> updatedByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @RequestBody @Valid UpdateRequestCityDto updateRequestCityDto
    ) {
        cityService.updateByRegionIdAndCityId(updateRequestCityDto, regionId, cityId);

        return ResponseEntity.ok(new RestResponseDto("City has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/regions/{regionId}/cities")
    public List<CityDto> getAllByRegionId(
            @PathVariable Long regionId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return cityService.getAllDtoByRegionId(regionId, sortQuery);
    }

    @PostMapping("/regions/{regionId}/cities")
    public ResponseEntity<RestResponseDto> addByRegionId(
            @PathVariable Long regionId,
            @RequestBody @Valid RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto
    ) {
        cityService.addByRegionId(requestCityWithoutRegionIdDto, regionId);

        return new ResponseEntity<>(new RestResponseDto("City has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
