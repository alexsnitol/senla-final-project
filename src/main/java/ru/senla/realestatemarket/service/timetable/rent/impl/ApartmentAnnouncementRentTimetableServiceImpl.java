package ru.senla.realestatemarket.service.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.mapper.timetable.rent.ApartmentAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IApartmentAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.timetable.rent.IApartmentAnnouncementRentTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasApartmentAnnouncementId;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement;
import static ru.senla.realestatemarket.repo.timetable.rent.specification.ApartmentAnnouncementRentTimetableSpecification.hasUserIdOfTenant;

@Slf4j
@Service
public class ApartmentAnnouncementRentTimetableServiceImpl
        extends AbstractRentTimetableServiceImpl<ApartmentAnnouncementRentTimetable>
        implements IApartmentAnnouncementRentTimetableService {

    private final IApartmentAnnouncementRentTimetableRepository apartmentAnnouncementRentTimetableRepository;
    private final IApartmentAnnouncementRepository apartmentAnnouncementRepository;
    private final IApartmentAnnouncementRentPurchaseRepository apartmentAnnouncementRentPurchaseRepository;

    private final ApartmentAnnouncementRentTimetableMapper timetableMapper
            = Mappers.getMapper(ApartmentAnnouncementRentTimetableMapper.class);


    public ApartmentAnnouncementRentTimetableServiceImpl(
            IUserRepository userRepository,
            IBalanceOperationService balanceOperationService,
            IApartmentAnnouncementRentTimetableRepository apartmentAnnouncementRentTimetableRepository,
            IApartmentAnnouncementRepository apartmentAnnouncementRepository,
            IApartmentAnnouncementRentPurchaseRepository apartmentAnnouncementRentPurchaseRepository
    ) {
        super(userRepository, balanceOperationService);
        this.apartmentAnnouncementRentTimetableRepository = apartmentAnnouncementRentTimetableRepository;
        this.apartmentAnnouncementRepository = apartmentAnnouncementRepository;
        this.apartmentAnnouncementRentPurchaseRepository = apartmentAnnouncementRentPurchaseRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementRentTimetableRepository);
        setClazz(ApartmentAnnouncementRentTimetable.class);
    }


    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByApartmentIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

        if (sortQuery == null) {
            apartmentAnnouncementRentTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementRentTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper
                .toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromApartmentAnnouncementRentTimetable(
                apartmentAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllByApartmentIdForUsersDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

        if (sortQuery == null) {
            apartmentAnnouncementRentTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementRentTimetables = getAll(hasApartmentAnnouncementId(apartmentAnnouncementId),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                        apartmentAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableDto> getAllOfCurrentTenantUserDto(String rsqlQuery, String sortQuery) {
        List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementRentTimetables = getAll(
                    hasUserIdOfTenant(UserUtil.getCurrentUserId()),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementRentTimetables = getAll(
                    hasUserIdOfTenant(UserUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toRentTimetableDtoFromApartmentAnnouncementTimetable(
                apartmentAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByApartmentAnnouncementIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementRentTimetables = getAll(
                    hasUserIdOfTenant(UserUtil.getCurrentUserId())
                            .and(hasApartmentAnnouncementId(apartmentAnnouncementId)),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementRentTimetables = getAll(
                    hasUserIdOfTenant(UserUtil.getCurrentUserId())
                            .and(hasApartmentAnnouncementId(apartmentAnnouncementId)),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                apartmentAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByApartmentAnnouncementIdDto(
            Long apartmentAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncementRentTimetable> apartmentAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementRentTimetables = getAll(
                    hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            apartmentAnnouncementId, UserUtil.getCurrentUserId()),
                    rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            apartmentAnnouncementRentTimetables = getAll(
                    hasApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            apartmentAnnouncementId, UserUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return timetableMapper.toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                apartmentAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public void addByApartmentAnnouncementIdWithoutPay(
            RequestRentTimetableWithUserIdOfTenantDto requestDto, Long apartmentAnnouncementId
    ) {
        ApartmentAnnouncement apartmentAnnouncement = apartmentAnnouncementRepository.findById(apartmentAnnouncementId);
        EntityHelper.checkEntityOnNull(apartmentAnnouncement, ApartmentAnnouncement.class, apartmentAnnouncementId);


        ApartmentAnnouncementRentTimetable timetable
                = timetableMapper.toApartmentAnnouncementRentTimetable(requestDto);


        timetable.setAnnouncement(apartmentAnnouncement);

        Long userIdOfTenant = requestDto.getUserIdOfTenant();
        User tenantUser = userRepository.findById(userIdOfTenant);
        EntityHelper.checkEntityOnNull(tenantUser, User.class, userIdOfTenant);

        timetable.setTenant(tenantUser);


        LocalDateTime specificFromDt = timetable.getFromDt();
        LocalDateTime specificToDt = timetable.getToDt();

        validateIntervalToAnnouncementType(apartmentAnnouncement, specificFromDt, specificToDt);

        checkForRecordsInSpecificIntervalExcludingIntervalItself(specificFromDt, specificToDt);

        apartmentAnnouncementRentTimetableRepository.create(timetable);
    }

    @Override
    @Transactional
    public void addByApartmentAnnouncementIdWithPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long apartmentAnnouncementId
    ) {
        ApartmentAnnouncement apartmentAnnouncement = apartmentAnnouncementRepository.findById(apartmentAnnouncementId);
        EntityHelper.checkEntityOnNull(apartmentAnnouncement, ApartmentAnnouncement.class, apartmentAnnouncementId);

        ApartmentAnnouncementRentTimetable timetable
                = timetableMapper.toApartmentAnnouncementRentTimetable(requestDto);


        timetable.setAnnouncement(apartmentAnnouncement);

        User currentTenantUser = userRepository.findById(UserUtil.getCurrentUserId());
        timetable.setTenant(currentTenantUser);


        LocalDateTime specificFromDt = timetable.getFromDt();
        LocalDateTime specificToDt = timetable.getToDt();

        validateIntervalToAnnouncementType(apartmentAnnouncement, specificFromDt, specificToDt);

        checkForRecordsInSpecificIntervalExcludingIntervalItself(specificFromDt, specificToDt);


        double finalSum = getFinalSumByAnnouncementAndInterval(
                apartmentAnnouncement, specificFromDt, specificToDt);

        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.setSum(-finalSum);
        balanceOperation.setComment(BalanceOperationCommentEnum.RENT.name());

        balanceOperationService.addFromCurrentUserAndApplyOperation(balanceOperation);


        apartmentAnnouncementRentTimetableRepository.create(timetable);


        ApartmentAnnouncementRentPurchase purchase = new ApartmentAnnouncementRentPurchase();
        purchase.setBalanceOperation(balanceOperation);
        purchase.setTimetable(timetable);

        apartmentAnnouncementRentPurchaseRepository.create(purchase);
    }

}
