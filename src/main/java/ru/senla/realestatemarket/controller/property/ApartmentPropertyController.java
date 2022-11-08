package ru.senla.realestatemarket.controller.property;

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
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/housing/apartments")
public class ApartmentPropertyController {

    private final IApartmentPropertyService apartmentPropertyService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping
    public List<ApartmentPropertyDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping
    public ResponseEntity<ApartmentPropertyDto> add(
            @RequestBody @Valid
            RequestApartmentPropertyWithUserIdOfOwnerDto requestApartmentPropertyWithUserIdOfOwnerDto
    ) {
        ApartmentPropertyDto response
                = apartmentPropertyService.addFromDto(requestApartmentPropertyWithUserIdOfOwnerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{id}")
    public ApartmentPropertyDto getById(
            @PathVariable Long id
    ) {
        return apartmentPropertyService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        apartmentPropertyService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment property has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid
            UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestApartmentPropertyWithUserIdOfOwnerDto
    ) {
        apartmentPropertyService.updateFromDtoById(updateRequestApartmentPropertyWithUserIdOfOwnerDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment property has been updated", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current")
    public List<ApartmentPropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentPropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/owners/current")
    public ResponseEntity<ApartmentPropertyDto> addFromCurrentUser(
            @RequestBody @Valid RequestApartmentPropertyDto requestApartmentPropertyDto
    ) {
        ApartmentPropertyDto response = apartmentPropertyService.addFromDtoFromCurrentUser(requestApartmentPropertyDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current/{id}")
    public ApartmentPropertyDto getByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return apartmentPropertyService.getDtoByIdOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PatchMapping("/owners/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdOfCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto
    ) {
        apartmentPropertyService.updateFromDtoByPropertyIdOfCurrentUser(updateRequestApartmentPropertyDto, id);

        return ResponseEntity.ok(new RestResponseDto(
                "Apartment property has been updated", HttpStatus.OK.value()));
    }

}
