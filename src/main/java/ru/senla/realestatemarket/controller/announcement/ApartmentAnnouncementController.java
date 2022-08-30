package ru.senla.realestatemarket.controller.announcement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
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


    @GetMapping
    public List<ApartmentAnnouncementDto> getAllApartmentAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return apartmentAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> publish(
            @RequestBody @Valid RequestApartmentAnnouncementDto requestApartmentAnnouncementDto
    ) {
        apartmentAnnouncementService.add(requestApartmentAnnouncementDto);

        return ResponseEntity.ok(new RestResponseDto(
                "Apartment announcement published", HttpStatus.OK.value()));
    }

}
