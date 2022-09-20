package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

import java.util.List;

public interface IApartmentAnnouncementRepository
        extends IAbstractHousingAnnouncementRepository<ApartmentAnnouncement> {
    List<ApartmentAnnouncement> findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    List<ApartmentAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    ApartmentAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner);

    ApartmentAnnouncement findByIdWithStatus(Long id, AnnouncementStatusEnum open);

}
