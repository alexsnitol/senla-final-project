package ru.senla.realestatemarket.controller.property;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/housing/apartments")
public class ApartmentPropertyController {

    private final IApartmentPropertyService apartmentPropertyService;


    @GetMapping
    public List<ApartmentPropertyDto> getAllApartmentProperties(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> addApartmentProperty(
            @RequestBody @Valid RequestApartmentPropertyDto requestApartmentPropertyDto
    ) {
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        apartmentPropertyService.add(requestApartmentPropertyDto, authorizedUser.getId());

        return ResponseEntity.ok(new RestResponseDto("Apartment property added", HttpStatus.OK.value()));
    }

}
