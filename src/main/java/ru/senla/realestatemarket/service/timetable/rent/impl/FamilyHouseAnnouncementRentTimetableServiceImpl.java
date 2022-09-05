package ru.senla.realestatemarket.service.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.service.timetable.rent.IFamilyHouseAnnouncementRentTimetableService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class FamilyHouseAnnouncementRentTimetableServiceImpl
        extends AbstractRentTimetableServiceImpl<FamilyHouseAnnouncementRentTimetable>
        implements IFamilyHouseAnnouncementRentTimetableService {

    private final IFamilyHouseAnnouncementRentTimetableRepository familyHouseAnnouncementRentTimetableRepository;


    public FamilyHouseAnnouncementRentTimetableServiceImpl(
            IFamilyHouseAnnouncementRentTimetableRepository familyHouseAnnouncementRentTimetableRepository) {
        this.familyHouseAnnouncementRentTimetableRepository = familyHouseAnnouncementRentTimetableRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementRentTimetableRepository);
        setClazz(FamilyHouseAnnouncementRentTimetable.class);
    }

}
