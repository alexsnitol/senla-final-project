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


    @GetMapping("/{id}")
    public FamilyHouseAnnouncementDto getById(
            @PathVariable Long id
    ) {
        return familyHouseAnnouncementService.getDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        familyHouseAnnouncementService.deleteById(id);

        return ResponseEntity.ok(
                new RestResponseDto("Family house announcement has been deleted", HttpStatus.OK.value()));
    }

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

    @GetMapping
    public List<FamilyHouseAnnouncementDto> getAllFamilyHouseAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return familyHouseAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto
    ) {
        familyHouseAnnouncementService.add(requestFamilyHouseAnnouncementDto);

        return new ResponseEntity<>(new RestResponseDto(
                "Family house announcement has been added with HIDDEN status",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
