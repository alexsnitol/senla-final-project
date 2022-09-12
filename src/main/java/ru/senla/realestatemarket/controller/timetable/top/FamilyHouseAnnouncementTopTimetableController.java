package ru.senla.realestatemarket.controller.timetable.top;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.service.timetable.top.IFamilyHouseAnnouncementTopTimetableService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/family-houses")
public class FamilyHouseAnnouncementTopTimetableController {

    private final IFamilyHouseAnnouncementTopTimetableService familyHouseAnnouncementTopTimetableService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{familyHouseAnnouncementId}/timetables/top")
    public List<TopTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseId(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementTopTimetableService.getAllByFamilyHouseIdDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/{familyHouseAnnouncementId}/timetables/top")
    public ResponseEntity<RestResponseDto> addByFamilyHouseIdWithoutPay(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestBody @Valid RequestTopTimetableDto requestTopTimetableDto
    ) {
        familyHouseAnnouncementTopTimetableService.addByFamilyHouseAnnouncementIdWithoutPay(
                requestTopTimetableDto, familyHouseAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Family house announcement top timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owner/current/{familyHouseAnnouncementId}/timetables/top")
    public List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseId(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementTopTimetableService.getAllOfCurrentUserByFamilyHouseIdDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/owner/current/{familyHouseAnnouncementId}/timetables/top")
    public ResponseEntity<RestResponseDto> addByFamilyHouseIdWithPayFromCurrentUser(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestBody @Valid RequestTopTimetableDto requestTopTimetableDto
    ) {
        familyHouseAnnouncementTopTimetableService.addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
                requestTopTimetableDto, familyHouseAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Family house announcement top timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }
    
}
