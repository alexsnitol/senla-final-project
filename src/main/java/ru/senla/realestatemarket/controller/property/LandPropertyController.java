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
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.property.ILandPropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/lands")
public class LandPropertyController {

    private final ILandPropertyService landPropertyService;


    @GetMapping
    public List<LandPropertyDto> getAllLandProperties(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestLandPropertyWithUserIdOfOwnerDto requestLandPropertyWithUserIdOfOwnerDto
    ) {
        landPropertyService.addFromDto(requestLandPropertyWithUserIdOfOwnerDto);

        return new ResponseEntity<>(new RestResponseDto("Land property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public LandPropertyDto getById(
            @PathVariable Long id
    ) {
        return landPropertyService.getDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        landPropertyService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Land property has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid
            UpdateRequestLandPropertyWithUserIdOfOwnerDto updateRequestLandPropertyWithUserIdOfOwnerDto
    ) {
        landPropertyService.updateFromDtoById(updateRequestLandPropertyWithUserIdOfOwnerDto, id);

        return ResponseEntity.ok(new RestResponseDto("Land property has been updated", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/current")
    public List<LandPropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landPropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/current")
    public ResponseEntity<RestResponseDto> addFromCurrentUser(
            @RequestBody @Valid RequestLandPropertyDto requestLandPropertyDto
    ) {
        landPropertyService.addFromDtoFromCurrentUser(requestLandPropertyDto);

        return new ResponseEntity<>(new RestResponseDto("Land property has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/current/{id}")
    public LandPropertyDto getByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return landPropertyService.getDtoByIdOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PutMapping("/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdOfCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestLandPropertyDto updateRequestLandPropertyDto
    ) {
        landPropertyService.updateFromDtoByPropertyIdOfCurrentUser(updateRequestLandPropertyDto, id);

        return ResponseEntity.ok(new RestResponseDto(
                "Land property has been updated", HttpStatus.OK.value()));
    }

}
