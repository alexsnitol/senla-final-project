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
import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.announcement.ILandAnnouncementService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/lands")
public class LandAnnouncementController {

    private final ILandAnnouncementService landAnnouncementService;


    @ApiOperation(
            value = "",
            notes = "Search by key words in all string fields"
    )
    @GetMapping("/search")
    public List<LandAnnouncementDto> getAllByKeyWords(
            @RequestParam(value = "keywords", required = false) String keyWords
    ) {
        return landAnnouncementService.getAllByKeyWords(keyWords);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping
    public List<LandAnnouncementDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Add new land announcement from DTO with HIDDEN status",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestLandAnnouncementDto requestLandAnnouncementDto
    ) {
        landAnnouncementService.addFromDto(requestLandAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Land announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping("/{id}")
    public LandAnnouncementDto getById(
            @PathVariable Long id
    ) {
        return landAnnouncementService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        landAnnouncementService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Land announcement has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto
    ) {
        landAnnouncementService.updateFromDtoById(updateRequestLandAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Land announcement has been updated", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            notes = "Get all land announcements with OPEN status"
    )
    @GetMapping("/open")
    public List<LandAnnouncementDto> getAllWithOpenStatus(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllWithOpenStatusDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Get land announcement by id with OPEN status"
    )
    @GetMapping("/open/{id}")
    public LandAnnouncementDto getByIdWithOpenStatus(
            @PathVariable Long id
    ) {
        return landAnnouncementService.getByIdWithOpenStatusDto(id);
    }

    @GetMapping("/closed/owner/{userIdOfOwner}")
    public List<LandAnnouncementDto> getAllWithClosedStatusByUserIfOfOwner(
            @PathVariable Long userIdOfOwner,
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllWithClosedStatusByUserIdOfOwnerDto(
                userIdOfOwner, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/owner/current")
    public List<LandAnnouncementDto> getAllOfCurrentUser(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @PostMapping("/owner/current")
    public ResponseEntity<RestResponseDto> addFromCurrentUser(
            @RequestBody @Valid RequestLandAnnouncementDto requestLandAnnouncementDto
    ) {
        landAnnouncementService.addFromDtoFromCurrentUser(requestLandAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Land announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("Authorized user")
    )
    @GetMapping("/owner/current/{id}")
    public LandAnnouncementDto getByIdOfCurrentUser(
            @PathVariable Long id
    ) {
        return landAnnouncementService.getByIdDtoOfCurrentUser(id);
    }

    @ApiOperation(
            value = "",
            notes = "Update land announcement by id with validation on access to update for current user",
            authorizations = @Authorization("Authorized user")
    )
    @PutMapping("/owner/current/{id}")
    public ResponseEntity<RestResponseDto> updateByIdFromCurrentUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto
    ) {
        landAnnouncementService.updateByIdFromCurrentUser(updateRequestLandAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Land announcement has been updated", HttpStatus.OK.value()));
    }

}
