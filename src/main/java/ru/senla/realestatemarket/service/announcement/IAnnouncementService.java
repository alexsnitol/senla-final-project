package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.AnnouncementDto;
import ru.senla.realestatemarket.model.announcement.Announcement;

import java.util.List;

public interface IAnnouncementService extends IAbstractAnnouncementService<Announcement> {

    List<AnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);
    List<AnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery);

}
