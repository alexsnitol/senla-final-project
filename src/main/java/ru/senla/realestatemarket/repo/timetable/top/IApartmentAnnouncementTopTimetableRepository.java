package ru.senla.realestatemarket.repo.timetable.top;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;

import java.time.LocalDateTime;
import java.util.List;

public interface IApartmentAnnouncementTopTimetableRepository
        extends IAbstractTopTimetableRepository<ApartmentAnnouncementTopTimetable> {
    List<ApartmentAnnouncementTopTimetable> findAllByApartmentAnnouncementId(
            Long apartmentAnnouncementId, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementTopTimetable> findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
            Long apartmentAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort);

    List<ApartmentAnnouncementTopTimetable>
    findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long apartmentAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort);
}
