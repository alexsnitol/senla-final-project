package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.List;

public interface IFamilyHouseAnnouncementRepository
        extends IAbstractHousingAnnouncementRepository<FamilyHouseAnnouncement> {

    List<FamilyHouseAnnouncement> findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);
    List<FamilyHouseAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    FamilyHouseAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner);

    FamilyHouseAnnouncement findByIdWithStatus(Long id, AnnouncementStatusEnum status);
}
