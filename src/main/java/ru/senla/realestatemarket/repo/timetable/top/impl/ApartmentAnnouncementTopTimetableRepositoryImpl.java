package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.top.specification.ApartmentAnnouncementTopTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.top.specification.ApartmentAnnouncementTopTimetableSpecification.hasApartmentAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.top.specification.ApartmentAnnouncementTopTimetableSpecification.hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class ApartmentAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<ApartmentAnnouncementTopTimetable>
        implements IApartmentAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementTopTimetable.class);
    }

    @Override
    public List<ApartmentAnnouncementTopTimetable> findAllByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasApartmentAnnouncementId(apartmentAnnouncementId),
                rsqlQuery, sort
        );
    }

    @Override
    public List<ApartmentAnnouncementTopTimetable>
    findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(
                hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        apartmentAnnouncementId, userIdOfOwner),
                rsqlQuery, sort
        );
    }

    @Override
    public List<ApartmentAnnouncementTopTimetable>
    findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long apartmentAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort
    ) {
        return findAll(
                Specification.where(
                        hasApartmentAnnouncementId(apartmentAnnouncementId)
                        .and(concernsTheIntervalBetweenSpecificFromAndTo(fromDt, toDt))),
                sort
        );
    }

}
