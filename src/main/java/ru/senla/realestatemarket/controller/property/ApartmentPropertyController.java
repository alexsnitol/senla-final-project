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
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/housing/apartments")
public class ApartmentPropertyController {

    private final IApartmentPropertyService apartmentPropertyService;


    @GetMapping("/{id}")
    public ApartmentPropertyDto getById(
            @PathVariable Long id
    ) {
        return apartmentPropertyService.getDtoById(id);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid
            RequestApartmentPropertyWithUserIdOfOwnerDto requestApartmentPropertyWithUserIdOfOwnerDto
    ) {
        apartmentPropertyService.add(requestApartmentPropertyWithUserIdOfOwnerDto);

        return new ResponseEntity<>(new RestResponseDto("Apartment property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        apartmentPropertyService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment property has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid
            UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestApartmentPropertyWithUserIdOfOwnerDto
    ) {
        apartmentPropertyService.updateById(updateRequestApartmentPropertyWithUserIdOfOwnerDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment property has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<ApartmentPropertyDto> getAll(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @GetMapping("/current")
    public List<ApartmentPropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentPropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @GetMapping("/current/{id}")
    public ApartmentPropertyDto getDtoByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return apartmentPropertyService.getDtoByIdOfCurrentUser(id);
    }

    @PostMapping("/current")
    public ResponseEntity<RestResponseDto> addFromCurrentUser(
            @RequestBody @Valid RequestApartmentPropertyDto requestApartmentPropertyDto
    ) {
        apartmentPropertyService.addFromCurrentUser(requestApartmentPropertyDto);

        return new ResponseEntity<>(new RestResponseDto("Apartment property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PutMapping("/current/{id}/delete-status")
    public ResponseEntity<RestResponseDto> deleteByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        apartmentPropertyService.setDeletedStatusByIdOfCurrentUser(id);

        return ResponseEntity.ok(new RestResponseDto(
                "Apartment property has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdOfCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto
    ) {
        apartmentPropertyService.updateByIdOfCurrentUser(updateRequestApartmentPropertyDto, id);

        return ResponseEntity.ok(new RestResponseDto(
                "Apartment property has been updated", HttpStatus.OK.value()));
    }

}
