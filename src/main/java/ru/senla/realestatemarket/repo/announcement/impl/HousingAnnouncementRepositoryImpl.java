package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IHousingAnnouncementRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class HousingAnnouncementRepositoryImpl
        extends AbstractHousingAnnouncementRepositoryImpl<HousingAnnouncement>
        implements IHousingAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(HousingAnnouncement.class);
    }


}
