package ru.senla.realestatemarket.controller.announcement;

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


    @GetMapping("/{id}")
    public LandAnnouncementDto getById(
            @PathVariable Long id
    ) {
        return landAnnouncementService.getDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        landAnnouncementService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Land announcement has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto
    ) {
        landAnnouncementService.updateById(updateRequestLandAnnouncementDto, id);

        return ResponseEntity.ok(
                new RestResponseDto("Land announcement has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<LandAnnouncementDto> getAllLandAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestLandAnnouncementDto requestLandAnnouncementDto
    ) {
        landAnnouncementService.add(requestLandAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Land announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
