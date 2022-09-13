package ru.senla.realestatemarket.controller.announcement;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.announcement.AnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.service.announcement.IAnnouncementService;
import ru.senla.realestatemarket.service.announcement.IHousingAnnouncementService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final IAnnouncementService announcementService;
    private final IHousingAnnouncementService housingAnnouncementService;


    @ApiOperation(
            value = "",
            notes = "Search by key words in all string fields"
    )
    @GetMapping("/search")
    public List<AnnouncementDto> getAllByKeyWords(
            @RequestParam(value = "keywords", required = false) String keyWords
    ) {
        return announcementService.getAllByKeyWords(keyWords);
    }

    @GetMapping
    public List<AnnouncementDto> getAllAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return announcementService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Get all announcement with OPEN status"
    )
    @GetMapping("/open")
    public List<AnnouncementDto> getAllOpenAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return announcementService.getAllWithOpenStatusDto(rsqlQuery, sortQuery);
    }

    @GetMapping("/housing")
    public List<HousingAnnouncementDto> getAllHousingAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return housingAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Get all housing announcement with OPEN status"
    )
    @GetMapping("/housing/open")
    public List<HousingAnnouncementDto> getAllOpenHousingAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return housingAnnouncementService.getAllWithOpenStatusDto(rsqlQuery, sortQuery);
    }

}
