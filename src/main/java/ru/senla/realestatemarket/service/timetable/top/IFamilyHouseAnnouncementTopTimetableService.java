package ru.senla.realestatemarket.service.timetable.top;

import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import javax.transaction.Transactional;
import java.util.List;

public interface IFamilyHouseAnnouncementTopTimetableService
        extends IAbstractTopTimetableService<FamilyHouseAnnouncementTopTimetable> {

    List<TopTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    );

    void addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestTopTimetableDto, Long familyHouseAnnouncementId
    );

}
