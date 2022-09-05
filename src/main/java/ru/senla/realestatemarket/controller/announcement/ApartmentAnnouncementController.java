package ru.senla.realestatemarket.controller.announcement;

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
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.announcement.IApartmentAnnouncementService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/apartments")
public class ApartmentAnnouncementController {

    private final IApartmentAnnouncementService apartmentAnnouncementService;


    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping("/{id}")
    public ApartmentAnnouncementDto getApartmentAnnouncementById(
            @PathVariable Long id
    ) {
        return apartmentAnnouncementService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteApartmentAnnouncementById(
            @PathVariable Long id
    ) {
        apartmentAnnouncementService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment announcement has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateApartmentAnnouncementById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto
    ) {
        apartmentAnnouncementService.updateById(updateRequestApartmentAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment announcement has been updated", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping
    public List<ApartmentAnnouncementDto> getAllApartmentAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Add new apartment announcement from DTO with HIDDEN status",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> addNewApartmentAnnouncement(
            @RequestBody @Valid RequestApartmentAnnouncementDto requestApartmentAnnouncementDto
    ) {
        apartmentAnnouncementService.addFromDto(requestApartmentAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            notes = "Get all apartment announcement with OPEN status"
    )
    @GetMapping("/open")
    public List<ApartmentAnnouncementDto> getAllOpenApartmentAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentAnnouncementService.getAllWithOpenStatusDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Get apartment announcement by id with OPEN status"
    )
    @GetMapping("/open/{id}")
    public ApartmentAnnouncementDto getByIdOpenApartmentAnnouncements(
            @PathVariable Long id
    ) {
        return apartmentAnnouncementService.getByIdWithOpenStatusDto(id);
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/current")
    public List<ApartmentAnnouncementDto> getAllApartmentAnnouncementsOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentAnnouncementService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("Authorized user")
    )
    @PostMapping("/current")
    public ResponseEntity<RestResponseDto> addApartmentAnnouncementsFromCurrentUser(
            @RequestBody @Valid RequestApartmentAnnouncementDto requestApartmentAnnouncementDto
    ) {
        apartmentAnnouncementService.addFromCurrentUser(requestApartmentAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            notes = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/current/{id}")
    public ApartmentAnnouncementDto getByIdApartmentAnnouncementOfCurrentUser(
            @PathVariable Long id
    ) {
        return apartmentAnnouncementService.getByIdDtoOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            notes = "Update apartment announcement by id with validation on access to update for current user",
            authorizations = @Authorization("Authorized user")
    )
    @PutMapping("/current/{id}")
    public ResponseEntity<RestResponseDto> updateApartmentAnnouncementByIdFromCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto
    ) {
        apartmentAnnouncementService.updateByIdFromCurrentUser(updateRequestApartmentAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Apartment announcement has been updated", HttpStatus.OK.value()));
    }

}
