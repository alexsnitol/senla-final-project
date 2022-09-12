package ru.senla.realestatemarket.service.timetable.top;

import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import java.util.List;

public interface ILandAnnouncementTopTimetableService
        extends IAbstractTopTimetableService<LandAnnouncementTopTimetable> {

    List<TopTimetableWithoutAnnouncementIdDto> getAllByLandIdDto(
            Long landAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByLandIdDto(
            Long landAnnouncementId, String rsqlQuery, String sortQuery);

    void addByLandAnnouncementIdWithoutPay(
            RequestTopTimetableDto requestDto, Long landAnnouncementId);
    void addByLandAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long landAnnouncementId
    );

}
