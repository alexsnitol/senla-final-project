package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

public interface IFamilyHouseAnnouncementService
        extends IAbstractHousingAnnouncementService<FamilyHouseAnnouncement, FamilyHouseAnnouncementDto> {

    void add(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);

}
