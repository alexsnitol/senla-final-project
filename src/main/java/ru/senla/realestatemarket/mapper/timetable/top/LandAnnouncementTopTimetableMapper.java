package ru.senla.realestatemarket.mapper.timetable.top;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
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

    public abstract LandAnnouncementTopTimetable toTopTimetableDtoFromLandAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromLandAnnouncementTopTimetable(
            LandAnnouncementTopTimetable landAnnouncementTopTimetable
    );

}
