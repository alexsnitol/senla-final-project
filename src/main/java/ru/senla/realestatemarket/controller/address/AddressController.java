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
import ru.senla.realestatemarket.dto.address.AddressDto;
import ru.senla.realestatemarket.dto.address.HouseNumberDto;
import ru.senla.realestatemarket.dto.address.RequestAddressDto;
import ru.senla.realestatemarket.dto.address.RequestHouseNumberDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.address.IStreetService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AddressController {

    private final IAddressService addressService;
    private final IStreetService streetService;


    @GetMapping("/addresses")
    public List<AddressDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return addressService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping("/addresses")
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestAddressDto requestAddressDto
    ) {
        addressService.add(requestAddressDto);

        return ResponseEntity.ok(new RestResponseDto("Address added", HttpStatus.OK.value()));
    }

    @GetMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}/house-numbers")
    public List<HouseNumberDto> getAllHouseNumbers(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        streetService.checkStreetOnExistByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        return addressService.getAllHouseNumbersDto(streetId, sortQuery);
    }

    @PostMapping("/regions/{regionId}/cities/{cityId}/streets/{streetId}/house-numbers")
    public ResponseEntity<RestResponseDto> getAllHouseNumbers(
            @PathVariable Long regionId,
            @PathVariable Long cityId,
            @PathVariable Long streetId,
            @RequestBody @Valid RequestHouseNumberDto requestHouseNumberDto
    ) {
        streetService.checkStreetOnExistByRegionIdAndCityIdAndStreetId(regionId, cityId, streetId);

        addressService.add(requestHouseNumberDto, streetId);

        return ResponseEntity.ok(new RestResponseDto("House number added", HttpStatus.OK.value()));
    }

}
