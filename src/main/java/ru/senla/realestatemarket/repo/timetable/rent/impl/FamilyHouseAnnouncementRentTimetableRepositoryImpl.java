package ru.senla.realestatemarket.repo.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class FamilyHouseAnnouncementRentTimetableRepositoryImpl
        extends AbstractRentTimetableRepositoryImpl<FamilyHouseAnnouncementRentTimetable>
        implements IFamilyHouseAnnouncementRentTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementRentTimetable.class);
    }

}
