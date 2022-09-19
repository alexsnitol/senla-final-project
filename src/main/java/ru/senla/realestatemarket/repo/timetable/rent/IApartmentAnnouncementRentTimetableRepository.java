package ru.senla.realestatemarket.repo.timetable.rent;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;

import java.util.List;

public interface IApartmentAnnouncementRentTimetableRepository
        extends IAbstractRentTimetableRepository<ApartmentAnnouncementRentTimetable> {

    List<ApartmentAnnouncementRentTimetable> findAllByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementRentTimetable> findAllWithOpenStatusByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementRentTimetable> findAllByUserIdOfTenant(
            Long userIdOfTenant, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementRentTimetable> findAllByUserIdOfTenantAndApartmentAnnouncementId(
            Long userIdOfTenant, Long apartmentAnnouncementId, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementRentTimetable> findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort);

}
