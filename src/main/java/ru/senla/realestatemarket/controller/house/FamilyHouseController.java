package ru.senla.realestatemarket.controller.house;

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
import ru.senla.realestatemarket.dto.house.FamilyHouseDto;
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.house.IFamilyHouseService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses/family-houses")
public class FamilyHouseController {

    private final IFamilyHouseService familyHouseService;


    @GetMapping("/{id}")
    public FamilyHouseDto getById(
            @PathVariable Long id
    ) {
        return familyHouseService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        familyHouseService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestFamilyHouseDto updateRequestFamilyHouseDto
    ) {
        familyHouseService.updateById(updateRequestFamilyHouseDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<FamilyHouseDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestFamilyHouseDto requestFamilyHouseDto
    ) {
        familyHouseService.addFromDto(requestFamilyHouseDto);

        return new ResponseEntity<>(new RestResponseDto("Family house has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
