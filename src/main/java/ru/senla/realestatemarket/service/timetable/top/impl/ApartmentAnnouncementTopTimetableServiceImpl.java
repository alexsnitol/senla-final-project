package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.mapper.timetable.ApartmentAnnouncementTopTimetableMapper;
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
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasId;
import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasUserIdOfOwnerInProperty;
import static ru.senla.realestatemarket.repo.timetable.specification.ApartmentAnnouncementTopTimetableSpecification.fromDtOrToDtMustBeBetweenSpecificFromAndTo;
import static ru.senla.realestatemarket.repo.timetable.specification.ApartmentAnnouncementTopTimetableSpecification.hasApartmentAnnouncementId;

@Slf4j
@Service
public class ApartmentAnnouncementTopTimetableServiceImpl
        extends AbstractTopTimetableServiceImpl<ApartmentAnnouncementTopTimetable>
        implements IApartmentAnnouncementTopTimetableService {

    private final IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository;
    private final IApartmentAnnouncementRepository apartmentAnnouncementRepository;
    private final IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository;

    private final ApartmentAnnouncementTopTimetableMapper timetableMapper
            = Mappers.getMapper(ApartmentAnnouncementTopTimetableMapper.class);


    public ApartmentAnnouncementTopTimetableServiceImpl(
            IUserRepository userRepository,
            IBalanceOperationService balanceOperationService,
            IApartmentAnnouncementTopTimetableRepository apartmentAnnouncementTopTimetableRepository,
            IApartmentAnnouncementRepository apartmentAnnouncementRepository,
            IApartmentAnnouncementTopPurchaseRepository apartmentAnnouncementTopPurchaseRepository,
            IAnnouncementTopPriceRepository announcementTopPriceRepository
    ) {
        super(userRepository, balanceOperationService, announcementTopPriceRepository);
        this.apartmentAnnouncementTopTimetableRepository = apartmentAnnouncementTopTimetableRepository;
        this.apartmentAnnouncementRepository = apartmentAnnouncementRepository;
        this.apartmentAnnouncementTopPurchaseRepository = apartmentAnnouncementTopPurchaseRepository;
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
            apartmentAnnouncementTopTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementTopTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(
                apartmentAnnouncementTopTimetables);
    }

    @Override
    @Transactional
    public void addByApartmentAnnouncementIdWithPayFromCurrentUser(
            RequestTopTimetableDto requestTopTimetableDto, Long apartmentAnnouncementId
    ) {
        ApartmentAnnouncement apartmentAnnouncement = apartmentAnnouncementRepository.findOne(
                hasId(apartmentAnnouncementId)
                        .and(hasUserIdOfOwnerInProperty(UserUtil.getCurrentUserId())));

        EntityHelper.checkEntityOnNull(apartmentAnnouncement, ApartmentAnnouncement.class, apartmentAnnouncementId);


        ApartmentAnnouncementTopTimetable apartmentAnnouncementTopTimetable
                = timetableMapper.toApartmentAnnouncementTopTimetable(requestTopTimetableDto);


        LocalDateTime specificFromDt = apartmentAnnouncementTopTimetable.getFromDt();
        LocalDateTime specificToDt = apartmentAnnouncementTopTimetable.getToDt();
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
        List<ApartmentAnnouncementTopTimetable> existingTimetablesInInterval
                = new ArrayList<>(apartmentAnnouncementTopTimetableRepository.findAll(
                hasApartmentAnnouncementId(apartmentAnnouncement.getId())
                        .and(fromDtOrToDtMustBeBetweenSpecificFromAndTo(specificFromDt, specificToDt)),
                Sort.by(Sort.Direction.ASC, "fromDt")));


        List<ApartmentAnnouncementTopTimetable> unoccupiedIntervalsOfTimetables = new LinkedList<>();

        if (existingTimetablesInInterval.isEmpty()) {
            unoccupiedIntervalsOfTimetables.add(
                    new ApartmentAnnouncementTopTimetable(apartmentAnnouncement, specificFromDt, specificToDt));
        } else {
            LocalDateTime tmpFromDt = specificFromDt;
            LocalDateTime tmpToDt;

            for (ApartmentAnnouncementTopTimetable timetable : existingTimetablesInInterval) {
                if (timetable.getFromDt().isAfter(specificFromDt) && timetable.getFromDt().isBefore(specificToDt)) {
                    tmpToDt = timetable.getFromDt();

                    unoccupiedIntervalsOfTimetables.add(
                            new ApartmentAnnouncementTopTimetable(apartmentAnnouncement, tmpFromDt, tmpToDt));
                }

                if (timetable.getToDt().isAfter(specificFromDt) && timetable.getToDt().isBefore(specificToDt)) {
                    tmpFromDt = timetable.getToDt();

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


            ApartmentAnnouncementTopPurchase apartmentAnnouncementTopPurchase = new ApartmentAnnouncementTopPurchase();
            apartmentAnnouncementTopPurchase.setBalanceOperation(balanceOperation);
            apartmentAnnouncementTopPurchase.setTimetable(timetable);

            apartmentAnnouncementTopPurchaseRepository.create(apartmentAnnouncementTopPurchase);
        }
    }

}
