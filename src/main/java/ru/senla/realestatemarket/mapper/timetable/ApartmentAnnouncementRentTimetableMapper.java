package ru.senla.realestatemarket.mapper.timetable;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class ApartmentAnnouncementRentTimetableMapper {

    public abstract ApartmentAnnouncementRentTimetable toApartmentAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);

    public abstract RentTimetableDto toRentTimetableDtoFromApartmentAnnouncementTimetable(
            ApartmentAnnouncementRentTimetable apartmentAnnouncementRentTimetable
    );

    public abstract List<RentTimetableDto> toRentTimetableDtoFromApartmentAnnouncementTimetable(
            Collection<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables
    );

}
