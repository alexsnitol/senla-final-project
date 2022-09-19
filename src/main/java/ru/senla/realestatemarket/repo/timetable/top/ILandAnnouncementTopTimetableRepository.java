package ru.senla.realestatemarket.repo.timetable.top;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import java.time.LocalDateTime;
import java.util.List;

public interface ILandAnnouncementTopTimetableRepository
        extends IAbstractTopTimetableRepository<LandAnnouncementTopTimetable> {
    List<LandAnnouncementTopTimetable> findAllByLandAnnouncementId(
            Long landAnnouncementId, String rsqlQuery, Sort sort);

    List<LandAnnouncementTopTimetable> finaAllByLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long landAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort);

    List<LandAnnouncementTopTimetable> findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long landAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort);
}
