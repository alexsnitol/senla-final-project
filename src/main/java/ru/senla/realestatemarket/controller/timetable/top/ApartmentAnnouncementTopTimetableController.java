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
import ru.senla.realestatemarket.service.timetable.top.IApartmentAnnouncementTopTimetableService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/apartments/{apartmentAnnouncementId}/timetables/top")
public class ApartmentAnnouncementTopTimetableController {

    private final IApartmentAnnouncementTopTimetableService apartmentAnnouncementTopTimetableService;


    @GetMapping
    public List<TopTimetableWithoutAnnouncementIdDto> getAllApartmentAnnouncementTopTimetableByApartmentId(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementTopTimetableService.getAllByApartmentIdDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> addApartmentAnnouncementTopTimetableByApartmentIdWithPayFromCurrentUser(
            @PathVariable Long apartmentAnnouncementId,
            @RequestBody @Valid RequestTopTimetableDto requestTopTimetableDto
    ) {
        apartmentAnnouncementTopTimetableService.addByApartmentAnnouncementIdWithPayFromCurrentUser(
                requestTopTimetableDto, apartmentAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement top timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
