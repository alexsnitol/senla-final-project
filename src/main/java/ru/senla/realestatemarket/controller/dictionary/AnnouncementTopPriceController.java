package ru.senla.realestatemarket.controller.dictionary;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.dictionary.UpdateRequestAnnouncementTopPriceDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.service.dictionary.IAnnouncementTopPriceService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcement-top-prices")
public class AnnouncementTopPriceController {

    private final IAnnouncementTopPriceService announcementTopPriceService;


    @GetMapping
    public List<AnnouncementTopPrice> getAllAnnouncementTopPrices(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return announcementTopPriceService.getAll(rsqlQuery, sortQuery);
    }

    @GetMapping("/{id}")
    public AnnouncementTopPrice getAnnouncementTopPriceById(
            @PathVariable Long id
    ) {
        return announcementTopPriceService.getById(id);
    }

    @ApiOperation(
            value = "",
            notes = "Update price per hour of announcement top price by id",
            authorizations = {@Authorization("ADMIN")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateAnnouncementTopPriceById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestAnnouncementTopPriceDto updateRequestAnnouncementTopPriceDto
    ) {
        announcementTopPriceService.updateById(updateRequestAnnouncementTopPriceDto, id);

        return ResponseEntity.ok(new RestResponseDto(
                "Announcement top price has been updated", HttpStatus.OK.value()));
    }

}
