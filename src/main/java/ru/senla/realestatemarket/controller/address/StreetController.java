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
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestStreetDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.IStreetService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StreetController {

    private final IStreetService streetService;


    @GetMapping("/streets/{id}")
    public StreetDto getById(
            @PathVariable Long id
    ) {
        return streetService.getDtoById(id);
    }

    @DeleteMapping("/streets/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        streetService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Street has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/streets/{id}")
    public ResponseEntity<RestResponseDto> updatedById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestStreetDto updateRequestStreetDto
    ) {
        streetService.updateById(updateRequestStreetDto, id);

        return ResponseEntity.ok(new RestResponseDto("Street has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/streets")
    public List<StreetDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return streetService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping("/streets")
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestStreetDto requestStreetDto
    ) {
        streetService.add(requestStreetDto);

        return new ResponseEntity<>(new RestResponseDto("Street has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}")
    public StreetDto getByRegionIdAndCityIdAndStreetId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId
    ) {
        return streetService.getDtoByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);
    }

    @DeleteMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}")
    public ResponseEntity<RestResponseDto> deleteByRegionIdAndCityIdAndStreetId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId
    ) {
        streetService.deleteByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        return ResponseEntity.ok(new RestResponseDto("Street has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}")
    public ResponseEntity<RestResponseDto> updateByRegionIdAndCityIdAndStreetId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId,
            @RequestBody @Valid UpdateRequestStreetDto updateRequestStreetDto
    ) {
        streetService.updateByRegionIdAndCityIdAndByStreetId(updateRequestStreetDto, regionId, cityId, streetId);

        return ResponseEntity.ok(new RestResponseDto("Street has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}/streets")
    public List<StreetDto> getAllByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return streetService.getAllDtoByRegionIdAndCityId(regionId, cityId, sortQuery);
    }

    @PostMapping("/regions/{regionId}/cities/{cityId}/streets")
    public ResponseEntity<RestResponseDto> addByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @RequestBody @Valid RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto
    ) {
        streetService.addByRegionIdAndCityId(requestStreetWithoutCityIdDto, regionId, cityId);

        return new ResponseEntity<>(new RestResponseDto("Street has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
