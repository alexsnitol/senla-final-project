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
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.service.property.IFamilyHousePropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/housing/family-houses")
public class FamilyHousePropertyController {

    private final IFamilyHousePropertyService familyHousePropertyService;


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
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        familyHousePropertyService.add(requestFamilyHousePropertyDto, authorizedUser.getId());

        return ResponseEntity.ok(new RestResponseDto("Family house property added", HttpStatus.OK.value()));
    }

}
