package ru.senla.realestatemarket.mapper.timetable.rent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.mapper.announcement.AnnouncementMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {UserMapper.class, AnnouncementMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class ApartmentAnnouncementRentTimetableMapper {

    public abstract ApartmentAnnouncementRentTimetable toApartmentAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);

    public abstract ApartmentAnnouncementRentTimetable toApartmentAnnouncementRentTimetable(
            RequestRentTimetableWithUserIdOfTenantDto requestRentTimetableWithUserIdOfTenantDto);

    @Mapping(target = "announcementId", source = "announcement.id")
    public abstract RentTimetableDto toRentTimetableDtoFromApartmentAnnouncementTimetable(
            ApartmentAnnouncementRentTimetable apartmentAnnouncementRentTimetable
    );

    @Mapping(target = "announcementId", source = "announcement.id")
    public abstract List<RentTimetableDto> toRentTimetableDtoFromApartmentAnnouncementTimetable(
            Collection<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables
    );

    public abstract RentTimetableWithoutAnnouncementIdDto toRentTimetableWithoutAnnouncementIdDto(
            ApartmentAnnouncementRentTimetable apartmentAnnouncementRentTimetable
    );

    public abstract List<RentTimetableWithoutAnnouncementIdDto>
    toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
            Collection<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables
    );

    public abstract RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto
    toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromApartmentAnnouncementRentTimetable(
            ApartmentAnnouncementRentTimetable apartmentAnnouncementRentTimetable
    );

    public abstract List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto>
    toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromApartmentAnnouncementRentTimetable(
            Collection<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables
    );

}
