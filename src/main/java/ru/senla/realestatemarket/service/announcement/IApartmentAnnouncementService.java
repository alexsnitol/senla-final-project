package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

public interface IApartmentAnnouncementService
        extends IAbstractHousingAnnouncementService<ApartmentAnnouncement, ApartmentAnnouncementDto> {

    void add(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto);

}
