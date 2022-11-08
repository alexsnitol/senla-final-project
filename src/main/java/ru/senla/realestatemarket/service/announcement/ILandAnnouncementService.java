package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestLandAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;

import java.util.List;

public interface ILandAnnouncementService
        extends IAbstractAnnouncementService<LandAnnouncement> {

    List<LandAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);
    List<LandAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery);
    List<LandAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(
            Long useIdOfOwner, String rsqlQuery, String sortQuery);
    List<LandAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);
    List<LandAnnouncementDto> getAllByKeyWords(String keyWords);
    List<LandAnnouncementDto> getAllWithOpenStatusByKeyWords(String keyWords);

    LandAnnouncementDto getDtoById(Long id);
    LandAnnouncementDto getByIdWithOpenStatusDto(Long id);
    LandAnnouncementDto getByIdDtoOfCurrentUser(Long id);

    LandAnnouncementDto addFromDto(RequestLandAnnouncementDto requestLandAnnouncementDto);
    LandAnnouncementDto addFromDtoFromCurrentUser(RequestLandAnnouncementDto requestLandAnnouncementDto);

    void updateFromDtoById(UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto, Long id);
    void updateByIdFromCurrentUser(UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto,
                                   Long id);

}
