package ru.senla.realestatemarket.controller.address;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

/**
 * @author Alexander Slotin (@alexsnitol)
 */

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

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<RestResponseDto> deletedById(
            @PathVariable Long id
    ) {
        cityService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("City has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PatchMapping("/cities/{id}")
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

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping("/cities")
    public ResponseEntity<CityDto> add(
            @RequestBody @Valid RequestCityDto requestCityDto
    ) {
        CityDto response = cityService.add(requestCityDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}")
    public CityDto getByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId
    ) {
        return cityService.getDtoRegionIdAndByCityId(regionId, cityId);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/regions/{regionId}/cities/{cityId}")
    public ResponseEntity<RestResponseDto> deleteByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId
    ) {
        cityService.deleteRegionIdAndCityById(regionId, cityId);

        return ResponseEntity.ok(new RestResponseDto("City has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PatchMapping("/regions/{regionId}/cities/{cityId}")
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

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping("/regions/{regionId}/cities")
    public ResponseEntity<CityDto> addByRegionId(
            @PathVariable Long regionId,
            @RequestBody @Valid RequestCityWithoutRegionIdDto requestCityWithoutRegionIdDto
    ) {
        CityDto response = cityService.addByRegionId(requestCityWithoutRegionIdDto, regionId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
