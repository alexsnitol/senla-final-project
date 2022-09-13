package ru.senla.realestatemarket.repo.announcement;

import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;

import java.util.List;

public interface IApartmentAnnouncementRepository extends IAbstractHousingAnnouncementRepository<ApartmentAnnouncement> {
    List<ApartmentAnnouncement> findAllInTheTextFieldsOfWhichContainsTheKeys(List<String> searchKeys);
}
