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
import ru.senla.realestatemarket.service.timetable.top.ILandAnnouncementTopTimetableService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/lands")
public class LandAnnouncementTopTimetableController {

    private final ILandAnnouncementTopTimetableService landAnnouncementTopTimetableService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{landAnnouncementId}/timetables/top")
    public List<TopTimetableWithoutAnnouncementIdDto> getAllByLandId(
            @PathVariable Long landAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return landAnnouncementTopTimetableService.getAllByLandIdDto(
                landAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/{landAnnouncementId}/timetables/top")
    public ResponseEntity<RestResponseDto> addByLandIdWithoutPay(
            @PathVariable Long landAnnouncementId,
            @RequestBody @Valid RequestTopTimetableDto requestTopTimetableDto
    ) {
        landAnnouncementTopTimetableService.addByLandAnnouncementIdWithoutPay(
                requestTopTimetableDto, landAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Land announcement top timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current/{landAnnouncementId}/timetables/top")
    public List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByLandId(
            @PathVariable Long landAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return landAnnouncementTopTimetableService.getAllOfCurrentUserByLandIdDto(
                landAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/owners/current/{landAnnouncementId}/timetables/top")
    public ResponseEntity<RestResponseDto> addByLandIdWithPayFromCurrentUser(
            @PathVariable Long landAnnouncementId,
            @RequestBody @Valid RequestTopTimetableDto requestTopTimetableDto
    ) {
        landAnnouncementTopTimetableService.addByLandAnnouncementIdWithPayFromCurrentUser(
                requestTopTimetableDto, landAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Land announcement top timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }
    
}
