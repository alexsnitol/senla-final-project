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
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.announcement.IFamilyHouseAnnouncementService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/family-houses")
public class FamilyHouseAnnouncementController {

    private final IFamilyHouseAnnouncementService familyHouseAnnouncementService;


    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping
    public List<FamilyHouseAnnouncementDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Add new family house announcement from DTO with HIDDEN status",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.addFromDto(requestFamilyHouseAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Family house announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping("/{id}")
    public FamilyHouseAnnouncementDto getById(
            @PathVariable Long id
    ) {
        return familyHouseAnnouncementService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        familyHouseAnnouncementService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house announcement has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.updateById(updateRequestFamilyHouseAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house announcement has been updated", HttpStatus.OK.value())
        );
    }

    @ApiOperation(
            value = "",
            notes = "Get all family house announcements with OPEN status"
    )
    @GetMapping("/open")
    public List<FamilyHouseAnnouncementDto> getAllWithOpenStatus(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllWithOpenStatusDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Get family house announcements by id with OPEN status"
    )
    @GetMapping("/open/{id}")
    public FamilyHouseAnnouncementDto getByIdWithOpenStatus(
            @PathVariable Long id
    ) {
        return familyHouseAnnouncementService.getByIdWithOpenStatusDto(id);
    }

    @GetMapping("/closed/owner/{userIdOfOwner}")
    public List<FamilyHouseAnnouncementDto> getAllWithClosedStatusByUserIfOfOwner(
            @PathVariable Long userIdOfOwner,
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllWithClosedStatusByUserIdOfOwnerDto(
                userIdOfOwner, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/current")
    public List<FamilyHouseAnnouncementDto> getAllOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @PostMapping("/current")
    public ResponseEntity<RestResponseDto> addFromCurrentUser(
            @RequestBody @Valid RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.addFromCurrentUser(requestFamilyHouseAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "FamilyHouse announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/current/{id}")
    public FamilyHouseAnnouncementDto getByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return familyHouseAnnouncementService.getByIdDtoOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            notes = "Update family house announcement by id with validation on access to update for current user",
            authorizations = @Authorization("Authorized user")
    )
    @PutMapping("/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdFromCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.updateByIdFromCurrentUser(updateRequestFamilyHouseAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house announcement has been updated", HttpStatus.OK.value()));
    }

}
