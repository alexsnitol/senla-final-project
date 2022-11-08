package ru.senla.realestatemarket.service.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.mapper.timetable.rent.FamilyHouseAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IFamilyHouseAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.timetable.rent.IFamilyHouseAnnouncementRentTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class FamilyHouseAnnouncementRentTimetableServiceImpl
        extends AbstractRentTimetableServiceImpl<FamilyHouseAnnouncementRentTimetable>
        implements IFamilyHouseAnnouncementRentTimetableService {

    private final IFamilyHouseAnnouncementRentTimetableRepository familyHouseAnnouncementRentTimetableRepository;
    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final IFamilyHouseAnnouncementRentPurchaseRepository familyHouseAnnouncementRentPurchaseRepository;

    private final FamilyHouseAnnouncementRentTimetableMapper timetableMapper;


    public FamilyHouseAnnouncementRentTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService,
            IFamilyHouseAnnouncementRentTimetableRepository familyHouseAnnouncementRentTimetableRepository,
            IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
            IFamilyHouseAnnouncementRentPurchaseRepository familyHouseAnnouncementRentPurchaseRepository,
            FamilyHouseAnnouncementRentTimetableMapper timetableMapper
    ) {
        super(userRepository, userUtil, balanceOperationService);

        this.clazz = FamilyHouseAnnouncementRentTimetable.class;
        this.defaultRepository = familyHouseAnnouncementRentTimetableRepository;

        this.familyHouseAnnouncementRentTimetableRepository = familyHouseAnnouncementRentTimetableRepository;
        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.familyHouseAnnouncementRentPurchaseRepository = familyHouseAnnouncementRentPurchaseRepository;
        this.timetableMapper = timetableMapper;
    }


    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByFamilyHouseIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper
                .toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromFamilyHouseAnnouncementRentTimetable(
                familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdOnlyDateTimesDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllWithOpenStatusByFamilyHouseIdOnlyDateTimesDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllWithOpenStatusByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllWithOpenStatusByFamilyHouseAnnouncementId(
                            familyHouseAnnouncementId, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableDto> getAllOfCurrentTenantUserDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByUserIdOfTenant(
                            userUtil.getCurrentUserId(), rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByUserIdOfTenant(
                            userUtil.getCurrentUserId(), rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
                familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByFamilyHouseAnnouncementIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByUserIdOfTenantAndFamilyHouseAnnouncementId(
                            userUtil.getCurrentUserId(), familyHouseAnnouncementId,
                            rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByUserIdOfTenantAndFamilyHouseAnnouncementId(
                            userUtil.getCurrentUserId(), familyHouseAnnouncementId,
                            rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseAnnouncementIdDto(
            Long familyHouseAnnouncementId, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncementRentTimetable> familyHouseAnnouncementRentTimetables;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            familyHouseAnnouncementId, userUtil.getCurrentUserId(),
                            rsqlQuery, Sort.by(Sort.Direction.ASC, "fromDt"));
        } else {
            familyHouseAnnouncementRentTimetables = familyHouseAnnouncementRentTimetableRepository
                    .findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                            familyHouseAnnouncementId, userUtil.getCurrentUserId(),
                            rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return timetableMapper.toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                familyHouseAnnouncementRentTimetables);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdWithoutPay(
            RequestRentTimetableWithUserIdOfTenantDto requestDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementRepository.findById(familyHouseAnnouncementId);
        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);


        FamilyHouseAnnouncementRentTimetable timetable
                = timetableMapper.toFamilyHouseAnnouncementRentTimetable(requestDto);


        timetable.setAnnouncement(familyHouseAnnouncement);

        Long userIdOfTenant = requestDto.getUserIdOfTenant();
        User tenantUser = userRepository.findById(userIdOfTenant);
        EntityHelper.checkEntityOnNull(tenantUser, User.class, userIdOfTenant);

        timetable.setTenant(tenantUser);


        LocalDateTime specificFromDt = timetable.getFromDt();
        LocalDateTime specificToDt = timetable.getToDt();

        validateInterval(familyHouseAnnouncement, specificFromDt, specificToDt);

        checkForRecordsInSpecificIntervalExcludingIntervalItself(specificFromDt, specificToDt);

        familyHouseAnnouncementRentTimetableRepository.create(timetable);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdWithOpenStatusAndPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementRepository.findByIdWithStatus(
                        familyHouseAnnouncementId, AnnouncementStatusEnum.OPEN
        );
        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);

        addByFamilyHouseAnnouncementWithPayFromCurrentTenantUser(requestDto, familyHouseAnnouncement);
    }

    @Override
    @Transactional
    public void addByFamilyHouseAnnouncementIdAndPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, Long familyHouseAnnouncementId
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementRepository.findById(familyHouseAnnouncementId);
        EntityHelper.checkEntityOnNull(
                familyHouseAnnouncement, FamilyHouseAnnouncement.class, familyHouseAnnouncementId);

        addByFamilyHouseAnnouncementWithPayFromCurrentTenantUser(requestDto, familyHouseAnnouncement);
    }

    private void addByFamilyHouseAnnouncementWithPayFromCurrentTenantUser(
            RequestRentTimetableDto requestDto, FamilyHouseAnnouncement familyHouseAnnouncement
    ) {
        FamilyHouseAnnouncementRentTimetable timetable
                = timetableMapper.toFamilyHouseAnnouncementRentTimetable(requestDto);


        timetable.setAnnouncement(familyHouseAnnouncement);

        User currentTenantUser = userRepository.findById(userUtil.getCurrentUserId());
        timetable.setTenant(currentTenantUser);


        LocalDateTime specificFromDt = timetable.getFromDt();
        LocalDateTime specificToDt = timetable.getToDt();

        validateInterval(familyHouseAnnouncement, specificFromDt, specificToDt);

        checkForRecordsInSpecificIntervalExcludingIntervalItself(specificFromDt, specificToDt);


        double finalSum = getFinalSumByAnnouncementAndInterval(
                familyHouseAnnouncement, specificFromDt, specificToDt);

        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.setSum(-finalSum);
        balanceOperation.setComment(BalanceOperationCommentEnum.RENT.name());

        balanceOperationService.addFromCurrentUserAndApplyOperation(balanceOperation);


        familyHouseAnnouncementRentTimetableRepository.create(timetable);


        FamilyHouseAnnouncementRentPurchase purchase = new FamilyHouseAnnouncementRentPurchase();
        purchase.setBalanceOperation(balanceOperation);
        purchase.setTimetable(timetable);

        familyHouseAnnouncementRentPurchaseRepository.create(purchase);
    }

}
