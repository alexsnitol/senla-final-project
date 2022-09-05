package ru.senla.realestatemarket.mapper.timetable;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableDto;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;

import java.util.List;

@Mapper
public abstract class TimetableMapper {

    public abstract ApartmentAnnouncementRentTimetable toApartmentAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);
    
    public abstract RentTimetableDto toRentTimetableDtoFromApartmentAnnouncementTimetable(
            ApartmentAnnouncementRentTimetable apartmentAnnouncementRentTimetable
    );
    
    public abstract List<RentTimetableDto> toRentTimetableDtoFromApartmentAnnouncementTimetable(
            List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables
    );

    public abstract FamilyHouseAnnouncementRentTimetable toFamilyHouseAnnouncementRentTimetable(
            RequestRentTimetableDto requestRentTimetableDto);

    public abstract RentTimetableDto toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            FamilyHouseAnnouncementRentTimetable familyHouseAnnouncementRentTimetable
    );
    
    public abstract List<RentTimetableDto> toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
            List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables
    );


    public abstract ApartmentAnnouncementTopTimetable toApartmentAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromApartmentAnnouncementTimetable(
            ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
    );

    public abstract List<TopTimetableDto> toTopTimetableDtoFromApartmentAnnouncementTimetable(
            List<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables
    );

    public abstract FamilyHouseAnnouncementTopTimetable toFamilyHouseAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromFamilyHouseAnnouncementTopTimetable(
            FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
    );

    public abstract List<TopTimetableDto> toTopTimetableDtoFromFamilyHouseAnnouncementTopTimetable(
            List<FamilyHouseAnnouncementTopTimetable> familyHouseAnnouncementTopTimetables
    );

    public abstract LandAnnouncementTopTimetable toLandAnnouncementTopTimetable(
            RequestTopTimetableDto requestTopTimetableDto);

    public abstract TopTimetableDto toTopTimetableDtoFromLandAnnouncementTopTimetable(
            LandAnnouncementTopTimetable landAnnouncementTopTimetable
    );

    public abstract List<TopTimetableDto> toTopTimetableDtoFromLandAnnouncementTopTimetable(
            List<LandAnnouncementTopTimetable> landAnnouncementTopTimetables
    );

}
