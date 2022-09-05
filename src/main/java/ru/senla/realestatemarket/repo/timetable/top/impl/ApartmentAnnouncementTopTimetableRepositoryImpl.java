package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class ApartmentAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<ApartmentAnnouncementTopTimetable>
        implements IApartmentAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncementTopTimetable.class);
    }

}
