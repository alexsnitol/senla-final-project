package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;

import java.util.List;

public interface IFamilyHouseAnnouncementRepository
        extends IAbstractHousingAnnouncementRepository<FamilyHouseAnnouncement> {
    List<FamilyHouseAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    FamilyHouseAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner);
}
