package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

import java.util.List;

public interface IApartmentAnnouncementService
        extends IAbstractHousingAnnouncementService<ApartmentAnnouncement> {

    List<ApartmentAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery);
    List<ApartmentAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery);
    List<ApartmentAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(
            Long useIdOfOwner, String rsqlQuery, String sortQuery);
    List<ApartmentAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);
    List<ApartmentAnnouncementDto> getAllByKeyWords(String keyWords);

    ApartmentAnnouncementDto getDtoById(Long id);
    ApartmentAnnouncementDto getByIdWithOpenStatusDto(Long id);
    ApartmentAnnouncementDto getByIdDtoOfCurrentUser(Long id);

    void addFromDto(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto);
    void addFromDtoFromCurrentUser(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto);

    void updateFromDtoById(UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto, Long id);
    void updateByIdFromDtoFromCurrentUser(UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto,
                                          Long id);

}
