package ru.senla.realestatemarket.controller.house;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.house.ApartmentHouseDto;
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses/apartment-houses")
public class ApartmentHouseController {

    private final IApartmentHouseService apartmentHouseService;


    @GetMapping
    public List<ApartmentHouseDto> getAllApartmentHouse(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentHouseService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestApartmentHouseDto requestApartmentHouseDto
    ) {
        apartmentHouseService.add(requestApartmentHouseDto);

        return ResponseEntity.ok(new RestResponseDto("Apartment house added", HttpStatus.OK.value()));
    }

}
