package ru.senla.realestatemarket.service.timetable.top;

import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import java.util.List;

public interface IFamilyHouseAnnouncementTopTimetableService
        extends IAbstractTopTimetableService<FamilyHouseAnnouncementTopTimetable> {

    List<TopTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery);

    void addByFamilyHouseAnnouncementIdWithoutPay(
            RequestTopTimetableDto requestDto, Long familyHouseAnnouncementId);
    
    void addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long familyHouseAnnouncementId
    );

}
