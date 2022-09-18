package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.exception.SpecificIntervalFullyBusyException;
import ru.senla.realestatemarket.mapper.timetable.top.FamilyHouseAnnouncementTopTimetableMapper;
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
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification.intervalWithSpecificFromAndTo;

@Slf4j
@Service
public class FamilyHouseAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<FamilyHouseAnnouncementTopTimetable>
        implements IFamilyHouseAnnouncementTopTimetableService {

    private final IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository;
    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository;

    private final FamilyHouseAnnouncementTopTimetableMapper timetableMapper;


    public FamilyHouseAnnouncementTopTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService,
            IFamilyHouseAnnouncementTopTimetableRepository familyHouseAnnouncementTopTimetableRepository,
            IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
            IFamilyHouseAnnouncementTopPurchaseRepository familyHouseAnnouncementTopPurchaseRepository,
            IAnnouncementTopPriceRepository announcementTopPriceRepository,
            FamilyHouseAnnouncementTopTimetableMapper timetableMapper) {
        super(userRepository, userUtil, balanceOperationService, announcementTopPriceRepository);
        this.familyHouseAnnouncementTopTimetableRepository = familyHouseAnnouncementTopTimetableRepository;
        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.familyHouseAnnouncementTopPurchaseRepository = familyHouseAnnouncementTopPurchaseRepository;
        this.timetableMapper = timetableMapper;
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
            familyHouseAnnouncementTopTimetables = familyHouseAnnouncementTopTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementTopTimetables = familyHouseAnnouncementTopTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(
                familyHouseAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementTopTimetable> familyHouseAnnouncementTopTimetables;

        if (sortQuery == null) {
            familyHouseAnnouncementTopTimetables = familyHouseAnnouncementTopTimetableRepository.
                    findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
                            familyHouseAnnouncementId, userUtil.getCurrentUserId(),
                            rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementTopTimetables = familyHouseAnnouncementTopTimetableRepository.
                    findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
                            familyHouseAnnouncementId, userUtil.getCurrentUserId(),
                    rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(
                familyHouseAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdWithoutPay(
            RequestTopTimetableDto requestDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementRepository.findById(familyHouseAnnouncementId);
        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);


        FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
                = timetableMapper.toFamilyHouseAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = familyHouseAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = familyHouseAnnouncementTopTimetable.getToDt();

        checkForSpecificFromAndToHaveZerosMinutesAndSecondsAndNanoSeconds(specificFromDt, specificToDt);

        List<FamilyHouseAnnouncementTopTimetable> finalIntervalsOfFamilyHouseAnnouncementTopTimetables
                = getIntervalsOfTimetablesByFamilyHouseAnnouncement(
                familyHouseAnnouncement, specificFromDt, specificToDt);

        createTimetables(finalIntervalsOfFamilyHouseAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement = familyHouseAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(familyHouseAnnouncementId, userUtil.getCurrentUserId());

        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);


        FamilyHouseAnnouncementTopTimetable familyHouseAnnouncementTopTimetable
                = timetableMapper.toFamilyHouseAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = familyHouseAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = familyHouseAnnouncementTopTimetable.getToDt();

        checkForSpecificFromAndToHaveZerosMinutesAndSecondsAndNanoSeconds(specificFromDt, specificToDt);

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
        if (familyHouseAnnouncementTopTimetableRepository.isExist(
                intervalWithSpecificFromAndTo(specificFromDt, specificToDt))
        ) {
            String message = "Specific interval fully busy. Adding new interval is impossible.";

            log.error(message);
            throw new SpecificIntervalFullyBusyException(message);
        }

        List<FamilyHouseAnnouncementTopTimetable> existingTimetablesInInterval
                = new ArrayList<>(familyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        familyHouseAnnouncement.getId(), specificFromDt, specificToDt,
                        Sort.by(Sort.Direction.ASC, "fromDt")));


        List<FamilyHouseAnnouncementTopTimetable> unoccupiedIntervalsOfTimetables = new LinkedList<>();

        if (existingTimetablesInInterval.isEmpty()) {
            unoccupiedIntervalsOfTimetables.add(
                    new FamilyHouseAnnouncementTopTimetable(familyHouseAnnouncement, specificFromDt, specificToDt));
        } else if (
                // check the first interval on fully busy
                (existingTimetablesInInterval.get(0).getFromDt().isBefore(specificFromDt)
                        || existingTimetablesInInterval.get(0).getFromDt().equals(specificFromDt))
                &&
                (existingTimetablesInInterval.get(0).getToDt().isAfter(specificToDt)
                        || existingTimetablesInInterval.get(0).getToDt().equals(specificToDt))
        ) {
            String message = "Specific interval fully busy. Adding new interval is impossible.";

            log.error(message);
            throw new SpecificIntervalFullyBusyException(message);
        } else {
            LocalDateTime tmpFromDt = specificFromDt;
            LocalDateTime tmpToDt;

            for (FamilyHouseAnnouncementTopTimetable timetable : existingTimetablesInInterval) {
                LocalDateTime intervalFromDt = timetable.getFromDt();
                LocalDateTime intervalToDt = timetable.getToDt();

                if (intervalFromDt.isAfter(specificFromDt)
                        && (intervalFromDt.isBefore(specificToDt) || intervalFromDt.equals(specificToDt))
                ) {
                    tmpToDt = intervalFromDt;

                    unoccupiedIntervalsOfTimetables.add(
                            new FamilyHouseAnnouncementTopTimetable(familyHouseAnnouncement, tmpFromDt, tmpToDt));
                }

                if ((intervalToDt.isAfter(specificFromDt) || intervalToDt.equals(specificFromDt))
                        && intervalToDt.isBefore(specificToDt)
                ) {
                    tmpFromDt = intervalToDt;

                    // current timetable is the last interval?
                    if (existingTimetablesInInterval.get(existingTimetablesInInterval.size() - 1).equals(timetable)) {
                        unoccupiedIntervalsOfTimetables.add(
                                new FamilyHouseAnnouncementTopTimetable(familyHouseAnnouncement, tmpFromDt, specificToDt)
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

    private void createTimetables(
            List<FamilyHouseAnnouncementTopTimetable> finalIntervalsOfFamilyHouseAnnouncementTopTimetables
    ) {
        for (FamilyHouseAnnouncementTopTimetable timetable : finalIntervalsOfFamilyHouseAnnouncementTopTimetables) {
            familyHouseAnnouncementTopTimetableRepository.create(timetable);
        }
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
