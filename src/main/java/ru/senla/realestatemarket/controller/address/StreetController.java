package ru.senla.realestatemarket.controller.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.address.RequestStreetDto;
import ru.senla.realestatemarket.dto.address.RequestStreetWithoutCityIdDto;
import ru.senla.realestatemarket.dto.address.StreetDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.ICityService;
import ru.senla.realestatemarket.service.address.IStreetService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StreetController {

    private final IStreetService streetService;
    private final ICityService cityService;


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

        return ResponseEntity.ok(new RestResponseDto("Street added", HttpStatus.OK.value()));
    }

    @GetMapping("/cities/{cityId}/streets")
    public List<StreetDto> getAllByCityId(
            @PathVariable Long cityId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return streetService.getAllDtoByCityId(cityId, sortQuery);
    }

    @PostMapping("/cities/{cityId}/streets")
    public ResponseEntity<RestResponseDto> addByCityId(
            @PathVariable Long cityId,
            @RequestBody @Valid RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto
    ) {
        streetService.add(requestStreetWithoutCityIdDto, cityId);

        return ResponseEntity.ok(new RestResponseDto("Street added", HttpStatus.OK.value()));
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}/streets")
    public List<StreetDto> getAllByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        cityService.checkCityOnExistByRegionIdAndCityId(regionId, cityId);

        return streetService.getAllDtoByCityId(cityId, sortQuery);
    }

    @PostMapping("/regions/{regionId}/cities/{cityId}/streets")
    public ResponseEntity<RestResponseDto> addByRegionIdAndCityId(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @RequestBody @Valid RequestStreetWithoutCityIdDto requestStreetWithoutCityIdDto
    ) {
        cityService.checkCityOnExistByRegionIdAndCityId(regionId, cityId);

        streetService.add(requestStreetWithoutCityIdDto, cityId);

        return ResponseEntity.ok(new RestResponseDto("Street added", HttpStatus.OK.value()));
    }

}
