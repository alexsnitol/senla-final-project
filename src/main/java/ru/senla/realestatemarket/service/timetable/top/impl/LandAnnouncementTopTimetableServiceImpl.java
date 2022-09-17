package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.exception.SpecificIntervalFullyBusyException;
import ru.senla.realestatemarket.mapper.timetable.top.LandAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.LandAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.ILandAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.timetable.top.ILandAnnouncementTopTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasUserIdOfOwnerInProperty;
import static ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification.intervalWithSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.hasLandAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.top.specification.LandAnnouncementTopTimetableSpecification.hasLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement;

@Slf4j
@Service
public class LandAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<LandAnnouncementTopTimetable>
        implements ILandAnnouncementTopTimetableService {

    private final ILandAnnouncementTopTimetableRepository landAnnouncementTopTimetableRepository;
    private final ILandAnnouncementRepository landAnnouncementRepository;
    private final ILandAnnouncementTopPurchaseRepository landAnnouncementTopPurchaseRepository;

    private final LandAnnouncementTopTimetableMapper timetableMapper;


    public LandAnnouncementTopTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService,
            ILandAnnouncementTopTimetableRepository landAnnouncementTopTimetableRepository,
            ILandAnnouncementRepository landAnnouncementRepository,
            ILandAnnouncementTopPurchaseRepository landAnnouncementTopPurchaseRepository,
            IAnnouncementTopPriceRepository announcementTopPriceRepository,
            LandAnnouncementTopTimetableMapper timetableMapper) {
        super(userRepository, userUtil, balanceOperationService, announcementTopPriceRepository);
        this.landAnnouncementTopTimetableRepository = landAnnouncementTopTimetableRepository;
        this.landAnnouncementRepository = landAnnouncementRepository;
        this.landAnnouncementTopPurchaseRepository = landAnnouncementTopPurchaseRepository;
        this.timetableMapper = timetableMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(landAnnouncementTopTimetableRepository);
        setClazz(LandAnnouncementTopTimetable.class);
    }


    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllByLandIdDto(
            Long landAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<LandAnnouncementTopTimetable> landAnnouncementTopTimetables;

        if (sortQuery == null) {
            landAnnouncementTopTimetables = getAll(hasLandAnnouncementId(landAnnouncementId),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            landAnnouncementTopTimetables = getAll(hasLandAnnouncementId(landAnnouncementId),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromLandAnnouncementTopTimetable(
                landAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByLandIdDto(
            Long landAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<LandAnnouncementTopTimetable> landAnnouncementTopTimetables;

        if (sortQuery == null) {
            landAnnouncementTopTimetables = getAll(
                    hasLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            landAnnouncementId, userUtil.getCurrentUserId()),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            landAnnouncementTopTimetables = getAll(
                    hasLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            landAnnouncementId, userUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromLandAnnouncementTopTimetable(
                landAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByLandAnnouncementIdWithoutPay(RequestTopTimetableDto requestDto, Long landAnnouncementId) {
        LandAnnouncement landAnnouncement = landAnnouncementRepository.findById(landAnnouncementId);
        EntityHelper.checkEntityOnNull(landAnnouncement, LandAnnouncement.class, landAnnouncementId);


        LandAnnouncementTopTimetable landAnnouncementTopTimetable
                = timetableMapper.toTopTimetableDtoFromLandAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = landAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = landAnnouncementTopTimetable.getToDt();

        checkForSpecificFromAndToHaveZerosMinutesAndSecondsAndNanoSeconds(specificFromDt, specificToDt);

        List<LandAnnouncementTopTimetable> finalIntervalsOfLandAnnouncementTopTimetables
                = getIntervalsOfTimetablesByLandAnnouncement(
                landAnnouncement, specificFromDt, specificToDt);

        createTimetables(finalIntervalsOfLandAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByLandAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long landAnnouncementId
    ) {
        LandAnnouncement landAnnouncement = landAnnouncementRepository.findOne(
                hasId(landAnnouncementId)
                        .and(hasUserIdOfOwnerInProperty(userUtil.getCurrentUserId())));

        EntityHelper.checkEntityOnNull(landAnnouncement, LandAnnouncement.class, landAnnouncementId);


        LandAnnouncementTopTimetable landAnnouncementTopTimetable
                = timetableMapper.toTopTimetableDtoFromLandAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = landAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = landAnnouncementTopTimetable.getToDt();

        checkForSpecificFromAndToHaveZerosMinutesAndSecondsAndNanoSeconds(specificFromDt, specificToDt);

        List<LandAnnouncementTopTimetable> finalIntervalsOfLandAnnouncementTopTimetables
                = getIntervalsOfTimetablesByLandAnnouncement(
                landAnnouncement, specificFromDt, specificToDt);


        double finalSum = 0;
        List<Double> sumList = new ArrayList<>(finalIntervalsOfLandAnnouncementTopTimetables.size());

        AnnouncementTypeEnum announcementType = AnnouncementTypeEnum.valueOf(landAnnouncement.getType().name());
        AnnouncementTopPrice announcementTopPrice = announcementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.LAND, announcementType);

        for (LandAnnouncementTopTimetable timetable : finalIntervalsOfLandAnnouncementTopTimetables) {
            double sum = calculateSumByDateTimes(announcementTopPrice.getPricePerHour(),
                    timetable.getFromDt(), timetable.getToDt());

            finalSum += sum;

            sumList.add(sum);
        }

        checkBalanceOfCurrentUserToApplyOperationWithSpecificSum(finalSum);

        createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
                finalIntervalsOfLandAnnouncementTopTimetables, sumList);
    }

    private List<LandAnnouncementTopTimetable> getIntervalsOfTimetablesByLandAnnouncement(
            LandAnnouncement landAnnouncement, LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        if (landAnnouncementTopTimetableRepository.isExist(
                intervalWithSpecificFromAndTo(specificFromDt, specificToDt))
        ) {
            String message = "Specific interval fully busy. Adding new interval is impossible.";

            log.error(message);
            throw new SpecificIntervalFullyBusyException(message);
        }

        List<LandAnnouncementTopTimetable> existingTimetablesInInterval
                = new ArrayList<>(landAnnouncementTopTimetableRepository.findAll(
                hasLandAnnouncementId(landAnnouncement.getId())
                        .and(concernsTheIntervalBetweenSpecificFromAndTo(specificFromDt, specificToDt)),
                Sort.by(Sort.Direction.ASC, "fromDt")));


        List<LandAnnouncementTopTimetable> unoccupiedIntervalsOfTimetables = new LinkedList<>();

        if (existingTimetablesInInterval.isEmpty()) {
            unoccupiedIntervalsOfTimetables.add(
                    new LandAnnouncementTopTimetable(landAnnouncement, specificFromDt, specificToDt));
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

            for (LandAnnouncementTopTimetable timetable : existingTimetablesInInterval) {
                LocalDateTime intervalFromDt = timetable.getFromDt();
                LocalDateTime intervalToDt = timetable.getToDt();

                if (intervalFromDt.isAfter(specificFromDt)
                        && (intervalFromDt.isBefore(specificToDt) || intervalFromDt.equals(specificToDt))
                ) {
                    tmpToDt = intervalFromDt;

                    unoccupiedIntervalsOfTimetables.add(
                            new LandAnnouncementTopTimetable(landAnnouncement, tmpFromDt, tmpToDt));
                }

                if ((intervalToDt.isAfter(specificFromDt) || intervalToDt.equals(specificFromDt))
                        && intervalToDt.isBefore(specificToDt)
                ) {
                    tmpFromDt = intervalToDt;

                    // current timetable is the last interval?
                    if (existingTimetablesInInterval.get(existingTimetablesInInterval.size() - 1).equals(timetable)) {
                        unoccupiedIntervalsOfTimetables.add(
                                new LandAnnouncementTopTimetable(landAnnouncement, tmpFromDt, specificToDt)
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
            List<LandAnnouncementTopTimetable> finalIntervalsOfLandAnnouncementTopTimetables
    ) {
        for (LandAnnouncementTopTimetable timetable : finalIntervalsOfLandAnnouncementTopTimetables) {
            landAnnouncementTopTimetableRepository.create(timetable);
        }
    }
    
    private void createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
            List<LandAnnouncementTopTimetable> finalIntervalsOfLandAnnouncementTopTimetables,
            List<Double> sumList
    ) {
        int i = 0; // index of the sum list

        for (LandAnnouncementTopTimetable timetable : finalIntervalsOfLandAnnouncementTopTimetables) {
            double sum = sumList.get(i++);

            BalanceOperation balanceOperation = new BalanceOperation();
            balanceOperation.setSum(-sum);
            balanceOperation.setComment(BalanceOperationCommentEnum.TOP.name());

            balanceOperationService.addFromCurrentUserAndApplyOperation(balanceOperation);


            landAnnouncementTopTimetableRepository.create(timetable);


            LandAnnouncementTopPurchase landAnnouncementTopPurchase = new LandAnnouncementTopPurchase();
            landAnnouncementTopPurchase.setBalanceOperation(balanceOperation);
            landAnnouncementTopPurchase.setTimetable(timetable);

            landAnnouncementTopPurchaseRepository.create(landAnnouncementTopPurchase);
        }
    }

}
