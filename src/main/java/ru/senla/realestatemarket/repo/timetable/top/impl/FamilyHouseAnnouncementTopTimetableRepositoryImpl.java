package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class FamilyHouseAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<FamilyHouseAnnouncementTopTimetable>
        implements IFamilyHouseAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementTopTimetable.class);
    }

}
