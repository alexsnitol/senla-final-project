package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.service.timetable.top.IApartmentAnnouncementTopTimetableService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ApartmentAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<ApartmentAnnouncementTopTimetable>
        implements IApartmentAnnouncementTopTimetableService {

    private final IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository;


    public ApartmentAnnouncementTopTimetableServiceImpl(
            IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository) {
        this.apartmentAnnouncementTopTimetableRepository = apartmentAnnouncementTopTimetableRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementTopTimetableRepository);
        setClazz(ApartmentAnnouncementTopTimetable.class);
    }

}
