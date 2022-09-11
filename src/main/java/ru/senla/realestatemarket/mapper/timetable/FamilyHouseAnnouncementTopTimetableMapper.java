package ru.senla.realestatemarket.mapper.timetable;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class FamilyHouseAnnouncementTopTimetableMapper {

    public abstract FamilyHouseAnnouncementTopTimetable toFamilyHouseAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromFamilyHouseAnnouncementTopTimetable(
            FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
    );

    public abstract List<TopTimetableDto> toTopTimetableDtoFromFamilyHouseAnnouncementTopTimetable(
            Collection<FamilyHouseAnnouncementTopTimetable> familyHouseAnnouncementTopTimetables
    );

    public abstract TopTimetableWithoutAnnouncementIdDto toTopTimetableWithoutAnnouncementIdDto(
            FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
    );

    public abstract List<TopTimetableWithoutAnnouncementIdDto>
    toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(
            Collection<FamilyHouseAnnouncementTopTimetable> familyHouseAnnouncementTopTimetables
    );

}
