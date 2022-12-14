package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.repo.announcement.IAnnouncementRepository;

import javax.annotation.PostConstruct;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Repository
public class AnnouncementRepositoryImpl
        extends AbstractAnnouncementRepositoryImpl<Announcement>
        implements IAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(Announcement.class);
    }


}
