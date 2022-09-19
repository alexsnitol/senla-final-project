package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.hasLandAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.hasUserIdOfOwnerInPropertyInLandAnnouncement;

@Slf4j
@Repository
public class LandAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<LandAnnouncementTopTimetable>
        implements ILandAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(LandAnnouncementTopTimetable.class);
    }

    @Override
    public List<LandAnnouncementTopTimetable> findAllByLandAnnouncementId(
            Long landAnnouncementId, String rsqlQuery, Sort sort) {
        return findAllByQuery(hasLandAnnouncementId(landAnnouncementId),
                rsqlQuery, sort);
    }

    @Override
    public List<LandAnnouncementTopTimetable> finaAllByLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long landAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort
    ) {
        return findAllByQuery(hasLandAnnouncementId(landAnnouncementId)
                        .and(hasUserIdOfOwnerInPropertyInLandAnnouncement(userIdOfOwner)),
                rsqlQuery, sort);
    }

    @Override
    public List<LandAnnouncementTopTimetable> findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long landAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort
    ) {
        return findAll(hasLandAnnouncementId(landAnnouncementId)
                        .and(concernsTheIntervalBetweenSpecificFromAndTo(fromDt, toDt)),
                sort
        );
    }

}
