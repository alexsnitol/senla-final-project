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
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.house.IFamilyHouseService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses/family-houses")
public class FamilyHouseController {

    private final IFamilyHouseService familyHouseService;


    @GetMapping
    public List<FamilyHouseDto> getAllFamilyHouse(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestFamilyHouseDto requestFamilyHouseDto
    ) {
        familyHouseService.add(requestFamilyHouseDto);

        return ResponseEntity.ok(new RestResponseDto("Family house added", HttpStatus.OK.value()));
    }

}
