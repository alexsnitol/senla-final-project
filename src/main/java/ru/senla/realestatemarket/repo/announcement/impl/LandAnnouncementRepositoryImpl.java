package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

@Slf4j
@Repository
public class LandAnnouncementRepositoryImpl
        extends AbstractAnnouncementRepositoryImpl<LandAnnouncement>
        implements ILandAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(LandAnnouncement.class);
    }


    @Override
    public <T> void fetchSelection(From<T, LandAnnouncement> from) {
        from.fetch("property", JoinType.LEFT);
    }

}
