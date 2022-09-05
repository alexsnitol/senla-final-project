package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.service.timetable.top.IFamilyHouseAnnouncementTopTimetableService;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class FamilyHouseAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<FamilyHouseAnnouncementTopTimetable>
        implements IFamilyHouseAnnouncementTopTimetableService {

    private final IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository;


    public FamilyHouseAnnouncementTopTimetableServiceImpl(
            IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository) {
        this.familyHouseAnnouncementTopTimetableRepository = familyHouseAnnouncementTopTimetableRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementTopTimetableRepository);
        setClazz(FamilyHouseAnnouncementTopTimetable.class);
    }

}
