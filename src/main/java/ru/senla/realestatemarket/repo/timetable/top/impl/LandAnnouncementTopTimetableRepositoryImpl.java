package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class LandAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<LandAnnouncementTopTimetable>
        implements ILandAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(LandAnnouncementTopTimetable.class);
    }

}
