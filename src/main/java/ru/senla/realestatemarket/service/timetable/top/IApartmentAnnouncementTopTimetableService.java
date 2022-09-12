package ru.senla.realestatemarket.service.timetable.top;

import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;

import java.util.List;

public interface IApartmentAnnouncementTopTimetableService
        extends IAbstractTopTimetableService<ApartmentAnnouncementTopTimetable> {

    List<TopTimetableWithoutAnnouncementIdDto> getAllByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery);

    void addByApartmentAnnouncementIdWithoutPay(
            RequestTopTimetableDto requestDto, Long apartmentAnnouncementId);
    void addByApartmentAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long apartmentAnnouncementId
    );

}
