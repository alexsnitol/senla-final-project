package ru.senla.realestatemarket.mapper.timetable;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class LandAnnouncementTopTimetableMapper {

    public abstract List<TopTimetableDto> toTopTimetableDtoFromLandAnnouncementTopTimetable(
            Collection<LandAnnouncementTopTimetable> landAnnouncementTopTimetables
    );

    public abstract TopTimetableWithoutAnnouncementIdDto toTopTimetableWithoutAnnouncementIdDto(
            LandAnnouncementTopTimetable landAnnouncementTopTimetable
    );

    public abstract
    List<TopTimetableWithoutAnnouncementIdDto> toTopTimetableWithoutAnnouncementIdDtoFromLandAnnouncementTopTimetable(
            Collection<LandAnnouncementTopTimetable> landAnnouncementTopTimetables
    );

    public abstract LandAnnouncementTopTimetable toLandAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromLandAnnouncementTopTimetable(
            LandAnnouncementTopTimetable landAnnouncementTopTimetable
    );

}
