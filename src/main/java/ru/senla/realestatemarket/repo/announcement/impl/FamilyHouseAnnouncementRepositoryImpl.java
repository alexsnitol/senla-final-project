package ru.senla.realestatemarket.repo.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;

@Slf4j
@Repository
public class FamilyHouseAnnouncementRepositoryImpl
        extends AbstractHousingAnnouncementRepositoryImpl<FamilyHouseAnnouncement>
        implements IFamilyHouseAnnouncementRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncement.class);
    }


}
