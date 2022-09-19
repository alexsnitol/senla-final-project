package ru.senla.realestatemarket.repo.timetable.rent;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;

import java.util.List;

public interface IFamilyHouseAnnouncementRentTimetableRepository
        extends IAbstractRentTimetableRepository<FamilyHouseAnnouncementRentTimetable> {

    List<FamilyHouseAnnouncementRentTimetable> findAllByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementRentTimetable> findAllWithOpenStatusByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementRentTimetable> findAllByUserIdOfTenant(
            Long userIdOfTenant, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementRentTimetable> findAllByUserIdOfTenantAndFamilyHouseAnnouncementId(
            Long userIdOfTenant, Long familyHouseAnnouncementId, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementRentTimetable> findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long familyHouseAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort);
    
}
