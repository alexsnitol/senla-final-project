package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.service.timetable.top.ILandAnnouncementTopTimetableService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class LandAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<LandAnnouncementTopTimetable>
        implements ILandAnnouncementTopTimetableService {

    private final ILandAnnouncementTopTimetableRepository landAnnouncementTopTimetableRepository;


    public LandAnnouncementTopTimetableServiceImpl(
            ILandAnnouncementTopTimetableRepository landAnnouncementTopTimetableRepository) {
        this.landAnnouncementTopTimetableRepository = landAnnouncementTopTimetableRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(landAnnouncementTopTimetableRepository);
        setClazz(LandAnnouncementTopTimetable.class);
    }

}
