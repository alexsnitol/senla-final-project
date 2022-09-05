package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestLandAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

import java.util.List;

public interface ILandAnnouncementService
        extends IAbstractAnnouncementService<LandAnnouncement> {

    List<LandAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);

    LandAnnouncementDto getDtoById(Long id);

    void add(RequestLandAnnouncementDto requestLandAnnouncementDto);

    void updateById(UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto, Long id);

}
