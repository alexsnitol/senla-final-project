package ru.senla.realestatemarket.repo.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.top.specification.FamilyHouseAnnouncementTopTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.top.specification.FamilyHouseAnnouncementTopTimetableSpecification.hasFamilyHouseAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.top.specification.FamilyHouseAnnouncementTopTimetableSpecification.hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById;

@Slf4j
@Repository
public class FamilyHouseAnnouncementTopTimetableRepositoryImpl
        extends AbstractTopTimetableRepositoryImpl<FamilyHouseAnnouncementTopTimetable>
        implements IFamilyHouseAnnouncementTopTimetableRepository {

    @PostConstruct
    public void init() {
        setClazz(FamilyHouseAnnouncementTopTimetable.class);
    }

    @Override
    public List<FamilyHouseAnnouncementTopTimetable> findAllByFamilyHouseAnnouncementId(
            Long familyHouseAnnouncementId, String rsqlQuery, Sort sort) {
        return findAllByQuery(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId), rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementTopTimetable>
    findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
            Long familyHouseAnnouncementId, Long userIdOfOwner, String rsqlQuery, Sort sort) {
        return findAllByQuery(
                hasFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
                        familyHouseAnnouncementId, userIdOfOwner), rsqlQuery, sort);
    }

    @Override
    public List<FamilyHouseAnnouncementTopTimetable>
    findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
            Long familyHouseAnnouncementId, LocalDateTime fromDt, LocalDateTime toDt, Sort sort
    ) {
        return findAll(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId)
                .and(concernsTheIntervalBetweenSpecificFromAndTo(fromDt, toDt)),
                sort);
    }

}
