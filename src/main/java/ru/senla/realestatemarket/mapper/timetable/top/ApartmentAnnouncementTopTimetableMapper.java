package ru.senla.realestatemarket.mapper.timetable.top;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class ApartmentAnnouncementTopTimetableMapper {

    public abstract ApartmentAnnouncementTopTimetable toApartmentAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromApartmentAnnouncementTopTimetable(
            ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
    );

    public abstract List<TopTimetableDto> toTopTimetableDtoFromApartmentAnnouncementTopTimetable(
            Collection<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables
    );

    public abstract TopTimetableWithoutAnnouncementIdDto
    toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(
            ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
    );

    public abstract List<TopTimetableWithoutAnnouncementIdDto>
    toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(
            Collection<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables
    );

}
