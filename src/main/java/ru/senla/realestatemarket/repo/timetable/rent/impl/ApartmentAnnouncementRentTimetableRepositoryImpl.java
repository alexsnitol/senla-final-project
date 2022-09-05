package ru.senla.realestatemarket.repo.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class ApartmentAnnouncementRentTimetableRepositoryImpl
        extends AbstractRentTimetableRepositoryImpl<ApartmentAnnouncementRentTimetable>
        implements IApartmentAnnouncementRentTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementRentTimetable.class);
    }

}
