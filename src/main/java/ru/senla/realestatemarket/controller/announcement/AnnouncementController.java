package ru.senla.realestatemarket.controller.announcement;

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


    @GetMapping
    public List<AnnouncementDto> getAllAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return announcementService.getAllDto(rsqlQuery, sortQuery);
    }

    @GetMapping("/housing")
    public List<HousingAnnouncementDto> getAllHousingAnnouncements(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return housingAnnouncementService.getAllDto(rsqlQuery, sortQuery);
    }

}
