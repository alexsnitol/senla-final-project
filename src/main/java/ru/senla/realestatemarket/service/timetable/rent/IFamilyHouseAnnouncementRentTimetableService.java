package ru.senla.realestatemarket.service.timetable.rent;

import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;

import java.util.List;

public interface IFamilyHouseAnnouncementRentTimetableService
        extends IAbstractRentTimetableService<FamilyHouseAnnouncementRentTimetable> {
    
    List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<RentTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdOnlyDateTimesDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<RentTimetableWithoutAnnouncementIdDto> getAllWithOpenStatusByFamilyHouseIdOnlyDateTimesDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery);
    List<RentTimetableDto> getAllOfCurrentTenantUserDto(
            String rsqlQuery, String sortQuery);
    List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByFamilyHouseAnnouncementIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery);
    List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseAnnouncementIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery);

    void addByFamilyHouseAnnouncementIdWithoutPay(
            RequestRentTimetableWithUserIdOfTenantDto requestDto, Long familyHouseAnnouncementId);
    void addByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long familyHouseAnnouncementId
    );

}
