package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface ILandAnnouncementRepository extends IAbstractRepository<LandAnnouncement, Long> {

    List<LandAnnouncement> findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);
    List<LandAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);

    LandAnnouncement findByIdAndUserIdOfOwnerInProperty(Long id, Long userIdOfOwner);

    LandAnnouncement findByIdWithStatus(Long id, AnnouncementStatusEnum status);
}
