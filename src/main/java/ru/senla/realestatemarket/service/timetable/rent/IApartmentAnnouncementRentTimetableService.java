package ru.senla.realestatemarket.service.timetable.rent;

import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;

import java.util.List;

public interface IApartmentAnnouncementRentTimetableService
        extends IAbstractRentTimetableService<ApartmentAnnouncementRentTimetable> {


    List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<RentTimetableWithoutAnnouncementIdDto> getAllByApartmentIdOnlyDateTimesDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    );
    List<RentTimetableWithoutAnnouncementIdDto> getAllWithOpenStatusByApartmentIdOnlyDateTimesDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery);
    List<RentTimetableDto> getAllOfCurrentTenantUserDto(
            String rsqlQuery, String sortQuery);
    List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByApartmentAnnouncementIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery);
    List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByApartmentAnnouncementIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery);

    void addByApartmentAnnouncementIdWithoutPay(
            RequestRentTimetableWithUserIdOfTenantDto requestDto, Long apartmentAnnouncementId);

    void addByApartmentAnnouncementIdWithOpenStatusAndPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long apartmentAnnouncementId);
    void addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long apartmentAnnouncementId);

}
