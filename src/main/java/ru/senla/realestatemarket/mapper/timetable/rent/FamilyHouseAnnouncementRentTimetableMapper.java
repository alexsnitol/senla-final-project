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
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {UserMapper.class, AnnouncementMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class FamilyHouseAnnouncementRentTimetableMapper {

    public abstract FamilyHouseAnnouncementRentTimetable toFamilyHouseAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);

    public abstract FamilyHouseAnnouncementRentTimetable toFamilyHouseAnnouncementRentTimetable(
            RequestRentTimetableWithUserIdOfTenantDto requestRentTimetableWithUserIdOfTenantDto);

    @Mapping(target = "announcementId", source = "announcement.id")
    public abstract RentTimetableDto toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            FamilyHouseAnnouncementRentTimetable familyHouseAnnouncementRentTimetable
    );

    @Mapping(target = "announcementId", source = "announcement.id")
    public abstract List<RentTimetableDto> toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            Collection<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables
    );
    
    public abstract RentTimetableWithoutAnnouncementIdDto
    toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
            FamilyHouseAnnouncementRentTimetable familyHouseAnnouncementRentTimetable
    );

    public abstract List<RentTimetableWithoutAnnouncementIdDto>
    toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
            Collection<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables
    );

    public abstract RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto
    toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromFamilyHouseAnnouncementRentTimetable(
            FamilyHouseAnnouncementRentTimetable familyHouseAnnouncementRentTimetable
    );

    public abstract List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto>
    toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromFamilyHouseAnnouncementRentTimetable(
            Collection<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables
    );

}
