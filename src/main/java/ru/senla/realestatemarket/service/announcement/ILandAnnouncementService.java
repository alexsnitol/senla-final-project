package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

public interface ILandAnnouncementService
        extends IAbstractAnnouncementService<LandAnnouncement, LandAnnouncementDto> {

    void add(RequestLandAnnouncementDto requestLandAnnouncementDto);

}
