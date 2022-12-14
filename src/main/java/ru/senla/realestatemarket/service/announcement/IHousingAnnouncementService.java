package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;

import java.util.List;

public interface IHousingAnnouncementService
        extends IAbstractHousingAnnouncementService<HousingAnnouncement> {

    List<HousingAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);
    List<HousingAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery);

}
