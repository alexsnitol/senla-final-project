package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface ILandAnnouncementRepository extends IAbstractRepository<LandAnnouncement, Long> {
    List<LandAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    LandAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner);

}
