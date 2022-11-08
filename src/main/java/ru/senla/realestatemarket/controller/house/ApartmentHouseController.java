package ru.senla.realestatemarket.controller.house;

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
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses/apartment-houses")
public class ApartmentHouseController {

    private final IApartmentHouseService apartmentHouseService;


    @GetMapping("/{id}")
    public ApartmentHouseDto getById(
            @PathVariable Long id
    ) {
        return apartmentHouseService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        apartmentHouseService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment house has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PatchMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestApartmentHouseDto updateRequestApartmentHouseDto
    ) {
        apartmentHouseService.updateById(updateRequestApartmentHouseDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment house has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<ApartmentHouseDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentHouseService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<ApartmentHouseDto> add(
            @RequestBody @Valid RequestApartmentHouseDto requestApartmentHouseDto
    ) {
        ApartmentHouseDto response = apartmentHouseService.addFromDto(requestApartmentHouseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
