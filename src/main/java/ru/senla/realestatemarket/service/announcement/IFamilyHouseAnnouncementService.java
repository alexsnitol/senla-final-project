package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.List;

public interface IFamilyHouseAnnouncementService
        extends IAbstractHousingAnnouncementService<FamilyHouseAnnouncement> {

    List<FamilyHouseAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);
    List<FamilyHouseAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery);
    List<FamilyHouseAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(
            Long useIdOfOwner, String rsqlQuery, String sortQuery);
    List<FamilyHouseAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);
    List<FamilyHouseAnnouncementDto> getAllByKeyWords(String keyWords);
    List<FamilyHouseAnnouncementDto> getAllWithOpenStatusByKeyWords(String keyWords);

    FamilyHouseAnnouncementDto getDtoById(Long id);
    FamilyHouseAnnouncementDto getByIdWithOpenStatusDto(Long id);
    FamilyHouseAnnouncementDto getByIdDtoOfCurrentUser(Long id);

    FamilyHouseAnnouncementDto addFromDto(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);
    FamilyHouseAnnouncementDto addFromCurrentUser(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto);

    void updateById(UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto, Long id);
    void updateByIdFromCurrentUser(UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto,
                                   Long id);
}
