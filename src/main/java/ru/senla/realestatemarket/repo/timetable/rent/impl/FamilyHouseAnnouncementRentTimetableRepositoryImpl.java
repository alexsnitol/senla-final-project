package ru.senla.realestatemarket.repo.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.rent.specification.FamilyHouseAnnouncementRentTimetableSpecification.hasFamilyHouseAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.FamilyHouseAnnouncementRentTimetableSpecification.hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.FamilyHouseAnnouncementRentTimetableSpecification.hasStatusInAnnouncement;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.FamilyHouseAnnouncementRentTimetableSpecification.hasUserIdOfTenant;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class FamilyHouseAnnouncementRentTimetableRepositoryImpl
        extends AbstractRentTimetableRepositoryImpl<FamilyHouseAnnouncementRentTimetable>
        implements IFamilyHouseAnnouncementRentTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementRentTimetable.class);
    }
    
    
    @Override
    public List<FamilyHouseAnnouncementRentTimetable> findAllByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasFamilyHouseAnnouncementId(familyHouseAnnouncementId),
                rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementRentTimetable> findAllWithOpenStatusByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasStatusInAnnouncement(AnnouncementStatusEnum.OPEN)
                        .and(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId)),
                rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementRentTimetable> findAllByUserIdOfTenant(
            Long userIdOfTenant, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasUserIdOfTenant(userIdOfTenant),
                rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementRentTimetable> findAllByUserIdOfTenantAndFamilyHouseAnnouncementId(
            Long userIdOfTenant, Long familyHouseAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasUserIdOfTenant(userIdOfTenant)
                        .and(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId)),
                rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementRentTimetable>
    findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long familyHouseAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        familyHouseAnnouncementId, userIdOfOwner),
                rsqlQuery, sort);
    }

}
