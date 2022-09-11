package ru.senla.realestatemarket.controller.timetable.top;

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
@RequestMapping("/api/announcements/land/{landAnnouncementId}/timetables/top")
public class LandAnnouncementTopTimetableController {

    private final ILandAnnouncementTopTimetableService landAnnouncementTopTimetableService;


    @GetMapping
    public List<TopTimetableWithoutAnnouncementIdDto> getAllLandAnnouncementTopTimetableByLandId(
            @PathVariable Long landAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return landAnnouncementTopTimetableService.getAllByLandIdDto(
                landAnnouncementId, rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> addLandAnnouncementTopTimetableByLandIdWithPayFromCurrentUser(
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
