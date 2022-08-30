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
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
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


    @GetMapping
    public List<FamilyHouseAnnouncementDto> getAllFamilyHouseAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> publish(
            @RequestBody @Valid RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.add(requestFamilyHouseAnnouncementDto);

        return ResponseEntity.ok(new RestResponseDto(
                "Family house announcement published", HttpStatus.OK.value()));
    }

}
