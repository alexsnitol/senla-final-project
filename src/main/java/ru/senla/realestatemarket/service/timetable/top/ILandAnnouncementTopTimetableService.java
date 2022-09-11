package ru.senla.realestatemarket.service.timetable.top;

import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import javax.transaction.Transactional;
import java.util.List;

public interface ILandAnnouncementTopTimetableService
        extends IAbstractTopTimetableService<LandAnnouncementTopTimetable> {

    List<TopTimetableWithoutAnnouncementIdDto> getAllByLandIdDto(
            Long landAnnouncementId, String rsqlQuery, String sortQuery
    );

    void addByLandAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestTopTimetableDto, Long landAnnouncementId
    );

}
