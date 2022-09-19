package ru.senla.realestatemarket.repo.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasApartmentAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasStatusInAnnouncement;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasUserIdOfTenant;

@Slf4j
@Repository
public class ApartmentAnnouncementRentTimetableRepositoryImpl
        extends AbstractRentTimetableRepositoryImpl<ApartmentAnnouncementRentTimetable>
        implements IApartmentAnnouncementRentTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementRentTimetable.class);
    }

    @Override
    public List<ApartmentAnnouncementRentTimetable> findAllByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasApartmentAnnouncementId(apartmentAnnouncementId),
                rsqlQuery, sort);
    }

    @Override
    public List<ApartmentAnnouncementRentTimetable> findAllWithOpenStatusByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasStatusInAnnouncement(AnnouncementStatusEnum.OPEN)
                        .and(hasApartmentAnnouncementId(apartmentAnnouncementId)),
                rsqlQuery, sort);
    }

    @Override
    public List<ApartmentAnnouncementRentTimetable> findAllByUserIdOfTenant(
            Long userIdOfTenant, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasUserIdOfTenant(userIdOfTenant),
                rsqlQuery, sort);
    }

    @Override
    public List<ApartmentAnnouncementRentTimetable> findAllByUserIdOfTenantAndApartmentAnnouncementId(
            Long userIdOfTenant, Long apartmentAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasUserIdOfTenant(userIdOfTenant)
                        .and(hasApartmentAnnouncementId(apartmentAnnouncementId)),
                rsqlQuery, sort);
    }

    @Override
    public List<ApartmentAnnouncementRentTimetable>
    findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        apartmentAnnouncementId, userIdOfOwner),
                rsqlQuery, sort);
    }

}
