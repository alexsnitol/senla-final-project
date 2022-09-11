package ru.senla.realestatemarket.mapper.timetable;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class FamilyHouseAnnouncementRentTimetableMapper {

    public abstract FamilyHouseAnnouncementRentTimetable toFamilyHouseAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);

    public abstract RentTimetableDto toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            FamilyHouseAnnouncementRentTimetable familyHouseAnnouncementRentTimetable
    );

    public abstract List<RentTimetableDto> toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            Collection<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables
    );

}
