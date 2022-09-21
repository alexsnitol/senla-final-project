package ru.senla.realestatemarket.controller.address;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.dto.address.UpdateRequestAddressDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.IAddressService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AddressController {

    private final IAddressService addressService;


    @GetMapping("/addresses/{id}")
    public AddressDto getById(
            @PathVariable Long id
    ) {
        return addressService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        addressService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Address has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/addresses/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestAddressDto updateRequestAddressDto
    ) {
        addressService.updateById(updateRequestAddressDto, id);

        return ResponseEntity.ok(new RestResponseDto("Address has been updated", HttpStatus.OK.value()));
    }

    @GetMapping("/addresses")
    public List<AddressDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return addressService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping("/addresses")
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestAddressDto requestAddressDto
    ) {
        addressService.add(requestAddressDto);

        return new ResponseEntity<>(new RestResponseDto("Address has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}/house-numbers")
    public List<HouseNumberDto> getAllHouseNumbers(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return addressService
                .getAllHouseNumbersDtoByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}/house-numbers")
    public ResponseEntity<RestResponseDto> getAllHouseNumbers(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId,
            @RequestBody @Valid RequestHouseNumberDto requestHouseNumberDto
    ) {
        addressService.addByRegionIdAndCityIdAndStreetId(requestHouseNumberDto, regionId, cityId, streetId);

        return new ResponseEntity<>(new RestResponseDto("House number has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
