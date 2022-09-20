package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.exception.SpecificIntervalFullyBusyException;
import ru.senla.realestatemarket.mapper.timetable.top.ApartmentAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.IApartmentAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.timetable.top.IApartmentAnnouncementTopTimetableService;
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
public class ApartmentAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<ApartmentAnnouncementTopTimetable>
        implements IApartmentAnnouncementTopTimetableService {

    private final IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository;
    private final IApartmentAnnouncementRepository apartmentAnnouncementRepository;
    private final IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository;

    private final ApartmentAnnouncementTopTimetableMapper timetableMapper;


    public ApartmentAnnouncementTopTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService,
            IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository,
            IApartmentAnnouncementRepository apartmentAnnouncementRepository,
            IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository,
            IAnnouncementTopPriceRepository announcementTopPriceRepository,
            ApartmentAnnouncementTopTimetableMapper timetableMapper
    ) {
        super(userRepository, userUtil, balanceOperationService, announcementTopPriceRepository);
        this.apartmentAnnouncementTopTimetableRepository = apartmentAnnouncementTopTimetableRepository;
        this.apartmentAnnouncementRepository = apartmentAnnouncementRepository;
        this.apartmentAnnouncementTopPurchaseRepository = apartmentAnnouncementTopPurchaseRepository;
        this.timetableMapper = timetableMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementTopTimetableRepository);
        setClazz(ApartmentAnnouncementTopTimetable.class);
    }


    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables;

        if (sortQuery == null) {
            apartmentAnnouncementTopTimetables = apartmentAnnouncementTopTimetableRepository
                    .findAllByApartmentAnnouncementId(apartmentAnnouncementId,
                            rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementTopTimetables = apartmentAnnouncementTopTimetableRepository
                    .findAllByApartmentAnnouncementId(apartmentAnnouncementId,
                            rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(
                apartmentAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public List<TopTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementTopTimetable> apartmentAnnouncementTopTimetables;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementTopTimetables = apartmentAnnouncementTopTimetableRepository.
                    findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            apartmentAnnouncementId, userUtil.getCurrentUserId(),
                            rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementTopTimetables = apartmentAnnouncementTopTimetableRepository.
                    findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            apartmentAnnouncementId, userUtil.getCurrentUserId(),
                            rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(
                apartmentAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByApartmentAnnouncementIdWithoutPay(
            RequestTopTimetableDto requestDto, Long apartmentAnnouncementId
    ) {
        ApartmentAnnouncement apartmentAnnouncement = apartmentAnnouncementRepository.findById(apartmentAnnouncementId);
        EntityHelper.checkEntityOnNull(apartmentAnnouncement, ApartmentAnnouncement.class, apartmentAnnouncementId);


        ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
                = timetableMapper.toApartmentAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = apartmentAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = apartmentAnnouncementTopTimetable.getToDt();

        validateInterval(specificFromDt, specificToDt);

        List<ApartmentAnnouncementTopTimetable> finalIntervalsOfApartmentAnnouncementTopTimetables
                = getIntervalsOfTimetablesByApartmentAnnouncement(
                apartmentAnnouncement, specificFromDt, specificToDt);

        createTimetables(finalIntervalsOfApartmentAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByApartmentAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestDto, Long apartmentAnnouncementId
    ) {
        ApartmentAnnouncement apartmentAnnouncement = apartmentAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(apartmentAnnouncementId, userUtil.getCurrentUserId());

        EntityHelper.checkEntityOnNull(apartmentAnnouncement, ApartmentAnnouncement.class, apartmentAnnouncementId);


        ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
                = timetableMapper.toApartmentAnnouncementTopTimetable(requestDto);


        LocalDateTime specificFromDt = apartmentAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = apartmentAnnouncementTopTimetable.getToDt();

        validateInterval(specificFromDt, specificToDt);

        List<ApartmentAnnouncementTopTimetable> finalIntervalsOfApartmentAnnouncementTopTimetables
                = getIntervalsOfTimetablesByApartmentAnnouncement(
                        apartmentAnnouncement, specificFromDt, specificToDt);


        double finalSum = 0;
        List<Double> sumList = new ArrayList<>(finalIntervalsOfApartmentAnnouncementTopTimetables.size());

        AnnouncementTypeEnum announcementType = AnnouncementTypeEnum.valueOf(apartmentAnnouncement.getType().name());
        AnnouncementTopPrice announcementTopPrice = announcementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.APARTMENT, announcementType);

        for (ApartmentAnnouncementTopTimetable timetable : finalIntervalsOfApartmentAnnouncementTopTimetables) {
            double sum = calculateSumByDateTimes(announcementTopPrice.getPricePerHour(),
                    timetable.getFromDt(), timetable.getToDt());

            finalSum += sum;

            sumList.add(sum);
        }

        checkBalanceOfCurrentUserToApplyOperationWithSpecificSum(finalSum);

        createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
                finalIntervalsOfApartmentAnnouncementTopTimetables, sumList);
    }

    private List<ApartmentAnnouncementTopTimetable> getIntervalsOfTimetablesByApartmentAnnouncement(
            ApartmentAnnouncement apartmentAnnouncement, LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        if (apartmentAnnouncementTopTimetableRepository.isExist(
                intervalWithSpecificFromAndTo(specificFromDt, specificToDt))
        ) {
            String message = "Specific interval fully busy. Adding new interval is impossible.";

            log.error(message);
            throw new SpecificIntervalFullyBusyException(message);
        }

        List<ApartmentAnnouncementTopTimetable> existingTimetablesInInterval
                = new ArrayList<>(apartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        apartmentAnnouncement.getId(),
                        specificFromDt, specificToDt,
                        Sort.by(Sort.Direction.ASC, "fromDt"))
        );


        List<ApartmentAnnouncementTopTimetable> unoccupiedIntervalsOfTimetables = new LinkedList<>();

        if (existingTimetablesInInterval.isEmpty()) {
            unoccupiedIntervalsOfTimetables.add(
                    new ApartmentAnnouncementTopTimetable(apartmentAnnouncement, specificFromDt, specificToDt));
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

            for (ApartmentAnnouncementTopTimetable timetable : existingTimetablesInInterval) {
                LocalDateTime intervalFromDt = timetable.getFromDt();
                LocalDateTime intervalToDt = timetable.getToDt();

                if (intervalFromDt.isAfter(specificFromDt)
                        && (intervalFromDt.isBefore(specificToDt) || intervalFromDt.equals(specificToDt))
                ) {
                    tmpToDt = intervalFromDt;

                    unoccupiedIntervalsOfTimetables.add(
                            new ApartmentAnnouncementTopTimetable(apartmentAnnouncement, tmpFromDt, tmpToDt));
                }

                if ((intervalToDt.isAfter(specificFromDt) || intervalToDt.equals(specificFromDt))
                        && intervalToDt.isBefore(specificToDt)
                ) {
                    tmpFromDt = intervalToDt;

                    // current timetable is the last interval?
                    if (existingTimetablesInInterval.get(existingTimetablesInInterval.size() - 1).equals(timetable)) {
                        unoccupiedIntervalsOfTimetables.add(
                                new ApartmentAnnouncementTopTimetable(apartmentAnnouncement, tmpFromDt, specificToDt)
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
            List<ApartmentAnnouncementTopTimetable> finalIntervalsOfApartmentAnnouncementTopTimetables
    ) {
        for (ApartmentAnnouncementTopTimetable timetable : finalIntervalsOfApartmentAnnouncementTopTimetables) {
            apartmentAnnouncementTopTimetableRepository.create(timetable);
        }
    }

    private void createTimetablesAndPurchasesAndApplyOperationsWithSumsFromTheListFromCurrentUser(
            List<ApartmentAnnouncementTopTimetable> finalIntervalsOfApartmentAnnouncementTopTimetables,
            List<Double> sumList
    ) {
        int i = 0; // index of the sum list

        for (ApartmentAnnouncementTopTimetable timetable : finalIntervalsOfApartmentAnnouncementTopTimetables) {
            double sum = sumList.get(i++);

            BalanceOperation balanceOperation = new BalanceOperation();
            balanceOperation.setSum(-sum);
            balanceOperation.setComment(BalanceOperationCommentEnum.TOP.name());

            balanceOperationService.addFromCurrentUserAndApplyOperation(balanceOperation);


            apartmentAnnouncementTopTimetableRepository.create(timetable);


            ApartmentAnnouncementTopPurchase purchase = new ApartmentAnnouncementTopPurchase();
            purchase.setBalanceOperation(balanceOperation);
            purchase.setTimetable(timetable);

            apartmentAnnouncementTopPurchaseRepository.create(purchase);
        }
    }

}
