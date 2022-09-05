package ru.senla.realestatemarket.service.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.service.timetable.rent.IApartmentAnnouncementRentTimetableService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ApartmentAnnouncementRentTimetableServiceImpl
        extends AbstractRentTimetableServiceImpl<ApartmentAnnouncementRentTimetable>
        implements IApartmentAnnouncementRentTimetableService {

    private final IApartmentAnnouncementRentTimetableRepository apartmentAnnouncementRentTimetableRepository;


    public ApartmentAnnouncementRentTimetableServiceImpl(
            IApartmentAnnouncementRentTimetableRepository apartmentAnnouncementRentTimetableRepository) {
        this.apartmentAnnouncementRentTimetableRepository = apartmentAnnouncementRentTimetableRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementRentTimetableRepository);
        setClazz(ApartmentAnnouncementRentTimetable.class);
    }

}
