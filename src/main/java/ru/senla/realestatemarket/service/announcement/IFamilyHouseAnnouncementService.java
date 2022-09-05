package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.List;

public interface IFamilyHouseAnnouncementService
        extends IAbstractHousingAnnouncementService<FamilyHouseAnnouncement> {

    List<FamilyHouseAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);

    FamilyHouseAnnouncementDto getDtoById(Long id);

    void add(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);

    void updateById(UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto, Long id);

}
