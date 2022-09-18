package ru.senla.realestatemarket.controller.property;

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
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto;
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


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping
    public List<FamilyHousePropertyDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHousePropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid
            RequestFamilyHousePropertyWithUserIdOfOwnerDto requestFamilyHousePropertyWithUserIdOfOwnerDto
    ) {
        familyHousePropertyService.addFromDto(requestFamilyHousePropertyWithUserIdOfOwnerDto);

        return new ResponseEntity<>(new RestResponseDto("Family house property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{id}")
    public FamilyHousePropertyDto getById(
            @PathVariable Long id
    ) {
        return familyHousePropertyService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        familyHousePropertyService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house property has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid
            UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto updateRequestFamilyHousePropertyWithUserIdOfOwnerDto
    ) {
        familyHousePropertyService.updateByIdFromDto(updateRequestFamilyHousePropertyWithUserIdOfOwnerDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house property has been updated", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current")
    public List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHousePropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/owners/current")
    public ResponseEntity<RestResponseDto> addFromCurrentUser(
            @RequestBody @Valid RequestFamilyHousePropertyDto requestFamilyHousePropertyDto
    ) {
        familyHousePropertyService.addFromDtoFromCurrentUser(requestFamilyHousePropertyDto);

        return new ResponseEntity<>(new RestResponseDto("Family house property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current/{id}")
    public FamilyHousePropertyDto getDtoByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return familyHousePropertyService.getDtoByIdOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PutMapping("/owners/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdOfCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestFamilyHousePropertyDto updateRequestFamilyHousePropertyDto
    ) {
        familyHousePropertyService.updateFromDtoByPropertyIdOfCurrentUser(updateRequestFamilyHousePropertyDto, id);

        return ResponseEntity.ok(new RestResponseDto(
                "Family house property has been updated", HttpStatus.OK.value()));
    }

}
