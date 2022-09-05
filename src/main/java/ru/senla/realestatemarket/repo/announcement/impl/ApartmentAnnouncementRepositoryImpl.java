package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class ApartmentAnnouncementRepositoryImpl
        extends AbstractHousingAnnouncementRepositoryImpl<ApartmentAnnouncement>
        implements IApartmentAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(ApartmentAnnouncement.class);
    }


}
