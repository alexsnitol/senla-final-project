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
import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
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


    @GetMapping
    public List<LandAnnouncementDto> getAllLandAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> publish(
            @RequestBody @Valid RequestLandAnnouncementDto requestLandAnnouncementDto
    ) {
        landAnnouncementService.add(requestLandAnnouncementDto);

        return ResponseEntity.ok(new RestResponseDto("Land announcement published", HttpStatus.OK.value()));
    }

}
