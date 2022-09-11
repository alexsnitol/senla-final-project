package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.mapper.timetable.FamilyHouseAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.IFamilyHouseAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.timetable.top.IFamilyHouseAnnouncementTopTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement
        .specification.FamilyHouseAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement
        .specification.FamilyHouseAnnouncementSpecification.hasUserIdOfOwnerInProperty;
import static ru.senla.realestatemarket.repo.timetable
        .specification.FamilyHouseAnnouncementTopTimetableSpecification.fromDtOrToDtMustBeBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable
        .specification.FamilyHouseAnnouncementTopTimetableSpecification.hasFamilyHouseAnnouncementId;

@Slf4j
@Service
public class FamilyHouseAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<FamilyHouseAnnouncementTopTimetable>
        implements IFamilyHouseAnnouncementTopTimetableService {

    private final IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository;
    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository;

    private final FamilyHouseAnnouncementTopTimetableMapper timetableMapper
            = Mappers.getMapper(FamilyHouseAnnouncementTopTimetableMapper.class);


    public FamilyHouseAnnouncementTopTimetableServiceImpl(
            IUserRepository userRepository,
            IBalanceOperationService balanceOperationService,
            IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository,
            IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
            IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository,
            IAnnouncementTopPriceRepository announcementTopPriceRepository
    ) {
        super(userRepository, balanceOperationService, announcementTopPriceRepository);
        this.familyHouseAnnouncementTopTimetableRepository = familyHouseAnnouncementTopTimetableRepository;
        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.familyHouseAnnouncementTopPurchaseRepository = familyHouseAnnouncementTopPurchaseRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementTopTimetableRepository);
        setClazz(FamilyHouseAnnouncementTopTimetable.class);
    }


    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementTopTimetable> familyHouseAnnouncementTopTimetables;

        if (sortQuery == null) {
            familyHouseAnnouncementTopTimetables = getAll(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementTopTimetables = getAll(hasFamilyHouseAnnouncementId(familyHouseAnnouncementId),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(
                familyHouseAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestTopTimetableDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement = familyHouseAnnouncementRepository.findOne(
                hasId(familyHouseAnnouncementId)
                        .and(hasUserIdOfOwnerInProperty(UserUtil.getCurrentUserId())));

        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);


        FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
                = timetableMapper.toFamilyHouseAnnouncementTopTimetable(requestTopTimetableDto);


        LocalDateTime specificFromDt = familyHouseAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = familyHouseAnnouncementTopTimetable.getToDt();
        List<FamilyHouseAnnouncementTopTimetable> finalIntervalsOfFamilyHouseAnnouncementTopTimetables
                = getIntervalsOfTimetablesByFamilyHouseAnnouncement(
                familyHouseAnnouncement, specificFromDt, specificToDt);


        double finalSum = 0;
        List<Double> sumList = new ArrayList<>(finalIntervalsOfFamilyHouseAnnouncementTopTimetables.size());

        AnnouncementTypeEnum announcementType = AnnouncementTypeEnum.valueOf(familyHouseAnnouncement.getType().name());
        AnnouncementTopPrice announcementTopPrice = announcementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.FAMILY_HOUSE, announcementType);

        for (FamilyHouseAnnouncementTopTimetable timetable : finalIntervalsOfFamilyHouseAnnouncementTopTimetables) {
            double sum = calculateSumByDateTimes(announcementTopPrice.getPricePerHour(),
                    timetable.getFromDt(), timetable.getToDt());

            finalSum += sum;

            sumList.add(sum);
        }

        checkBalanceOfCurrentUserToApplyOperationWithSpecificSum(finalSum);

        createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
                finalIntervalsOfFamilyHouseAnnouncementTopTimetables, sumList);
    }

    private List<FamilyHouseAnnouncementTopTimetable> getIntervalsOfTimetablesByFamilyHouseAnnouncement(
            FamilyHouseAnnouncement familyHouseAnnouncement, LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        List<FamilyHouseAnnouncementTopTimetable> existingTimetablesInInterval
                = new ArrayList<>(familyHouseAnnouncementTopTimetableRepository.findAll(
                hasFamilyHouseAnnouncementId(familyHouseAnnouncement.getId())
                        .and(fromDtOrToDtMustBeBetweenSpecificFromAndTo(specificFromDt, specificToDt)),
                Sort.by(Sort.Direction.ASC, "fromDt")));


        List<FamilyHouseAnnouncementTopTimetable> unoccupiedIntervalsOfTimetables = new LinkedList<>();

        if (existingTimetablesInInterval.isEmpty()) {
            unoccupiedIntervalsOfTimetables.add(
                    new FamilyHouseAnnouncementTopTimetable(familyHouseAnnouncement, specificFromDt, specificToDt));
        } else {
            LocalDateTime tmpFromDt = specificFromDt;
            LocalDateTime tmpToDt;

            for (FamilyHouseAnnouncementTopTimetable timetable : existingTimetablesInInterval) {
                if (timetable.getFromDt().isAfter(specificFromDt) && timetable.getFromDt().isBefore(specificToDt)) {
                    tmpToDt = timetable.getFromDt();

                    unoccupiedIntervalsOfTimetables.add(
                            new FamilyHouseAnnouncementTopTimetable(familyHouseAnnouncement, tmpFromDt, tmpToDt));
                }

                if (timetable.getToDt().isAfter(specificFromDt) && timetable.getToDt().isBefore(specificToDt)) {
                    tmpFromDt = timetable.getToDt();

                    // current timetable is the last interval?
                    if (existingTimetablesInInterval.get(existingTimetablesInInterval.size() - 1).equals(timetable)) {
                        unoccupiedIntervalsOfTimetables.add(
                                new FamilyHouseAnnouncementTopTimetable(
                                        familyHouseAnnouncement, tmpFromDt, specificToDt)
                        );
                    }
                } else {
                    // toDt of the current timetable greater than the specified interval
                    break;
                }
            }
        }

        return unoccupiedIntervalsOfTimetables;
    }

    private void createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
            List<FamilyHouseAnnouncementTopTimetable> finalIntervalsOfFamilyHouseAnnouncementTopTimetables,
            List<Double> sumList
    ) {
        int i = 0; // index of the sum list

        for (FamilyHouseAnnouncementTopTimetable timetable : finalIntervalsOfFamilyHouseAnnouncementTopTimetables) {
            double sum = sumList.get(i++);

            BalanceOperation balanceOperation = new BalanceOperation();
            balanceOperation.setSum(-sum);
            balanceOperation.setComment(BalanceOperationCommentEnum.TOP.name());

            balanceOperationService.addFromCurrentUserAndApplyOperation(balanceOperation);


            familyHouseAnnouncementTopTimetableRepository.create(timetable);


            FamilyHouseAnnouncementTopPurchase familyHouseAnnouncementTopPurchase
                    = new FamilyHouseAnnouncementTopPurchase();
            familyHouseAnnouncementTopPurchase.setBalanceOperation(balanceOperation);
            familyHouseAnnouncementTopPurchase.setTimetable(timetable);

            familyHouseAnnouncementTopPurchaseRepository.create(familyHouseAnnouncementTopPurchase);
        }
    }

}
