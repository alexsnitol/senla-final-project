package ru.senla.realestatemarket.controller.property;

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
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.property.IFamilyHousePropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/housing/family-houses")
public class FamilyHousePropertyController {

    private final IFamilyHousePropertyService familyHousePropertyService;


    @GetMapping("/{id}")
    public FamilyHousePropertyDto getById(
            @PathVariable Long id
    ) {
        return familyHousePropertyService.getDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        familyHousePropertyService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house property has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestFamilyHousePropertyDto updateRequestFamilyHousePropertyDto
    ) {
        familyHousePropertyService.updateById(updateRequestFamilyHousePropertyDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house property has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<FamilyHousePropertyDto> getAllFamilyHouseProperties(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHousePropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> addFamilyHouseProperty(
            @RequestBody @Valid RequestFamilyHousePropertyDto requestFamilyHousePropertyDto
    ) {
        familyHousePropertyService.addFromCurrentUser(requestFamilyHousePropertyDto);

        return new ResponseEntity<>(new RestResponseDto("Family house property has been added",
                HttpStatus.OK.value()), HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHousePropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

}
