package ru.senla.realestatemarket.repo.timetable.top;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import java.time.LocalDateTime;
import java.util.List;

public interface IFamilyHouseAnnouncementTopTimetableRepository
        extends IAbstractTopTimetableRepository<FamilyHouseAnnouncementTopTimetable> {

    List<FamilyHouseAnnouncementTopTimetable> findAllByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementTopTimetable>
    findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
            Long familyHouseAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort);

    List<FamilyHouseAnnouncementTopTimetable>
    findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long familyHouseAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort);
}
