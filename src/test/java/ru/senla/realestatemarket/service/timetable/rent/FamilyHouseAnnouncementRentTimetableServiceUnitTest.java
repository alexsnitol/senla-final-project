package ru.senla.realestatemarket.service.timetable.rent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.exception.InSpecificIntervalExistRecordsException;
import ru.senla.realestatemarket.exception.SpecificIntervalIsNotDailyException;
import ru.senla.realestatemarket.exception.SpecificIntervalIsNotMonthlyException;
import ru.senla.realestatemarket.mapper.timetable.rent.FamilyHouseAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.purchase.rent.FamilyHouseAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.timetable.rent.FamilyHouseAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IFamilyHouseAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class FamilyHouseAnnouncementRentTimetableServiceUnitTest {

    @Autowired
    IFamilyHouseAnnouncementRentTimetableService familyHouseAnnouncementRentTimetableService;

    @Autowired
    IFamilyHouseAnnouncementRentTimetableRepository mockedFamilyHouseAnnouncementRentTimetableRepository;
    @Autowired
    IFamilyHouseAnnouncementRepository mockedFamilyHouseAnnouncementRepository;
    @Autowired
    IFamilyHouseAnnouncementRentPurchaseRepository mockedFamilyHouseAnnouncementRentPurchaseRepository;

    @Autowired
    FamilyHouseAnnouncementRentTimetableMapper mockedTimetableMapper;

    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    IBalanceOperationService mockedBalanceOperationService;


    RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto
            = mock(RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto.class);
    FamilyHouseAnnouncementRentTimetable mockedFamilyHouseAnnouncementRentTimetable
            = mock(FamilyHouseAnnouncementRentTimetable.class);
    RentTimetableWithoutAnnouncementIdDto mockedRentTimetableWithoutAnnouncementIdDto
            = mock(RentTimetableWithoutAnnouncementIdDto.class);
    RequestRentTimetableWithUserIdOfTenantDto mockedRequestRentTimetableWithUserIdOfTenantDto
            = mock(RequestRentTimetableWithUserIdOfTenantDto.class);
    RequestRentTimetableDto mockedRequestRentTimetableDto
            = mock(RequestRentTimetableDto.class);
    RentTimetableDto mockedRentTimetableDto
            = mock(RentTimetableDto.class);
    FamilyHouseAnnouncement mockedFamilyHouseAnnouncement
            = mock(FamilyHouseAnnouncement.class);
    User mockedUser = mock(User.class);



    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedFamilyHouseAnnouncementRentTimetableRepository,
                mockedFamilyHouseAnnouncementRepository,
                mockedFamilyHouseAnnouncementRentPurchaseRepository,
                mockedTimetableMapper,
                mockedUserRepository,
                mockedUserUtil,
                mockedBalanceOperationService,
                mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto,
                mockedFamilyHouseAnnouncementRentTimetable,
                mockedRentTimetableWithoutAnnouncementIdDto,
                mockedRentTimetableDto,
                mockedUser,
                mockedFamilyHouseAnnouncement
        );
    }


    @Test
    void whenGetAllByFamilyHouseIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto);

        // test
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllByFamilyHouseAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> result
                = familyHouseAnnouncementRentTimetableService
                .getAllByFamilyHouseIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllByFamilyHouseIdOnlyDateTimesDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllByFamilyHouseAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = familyHouseAnnouncementRentTimetableService
                .getAllByFamilyHouseIdOnlyDateTimesDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllWithOpenStatusByFamilyHouseIdOnlyDateTimesDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllWithOpenStatusByFamilyHouseAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = familyHouseAnnouncementRentTimetableService
                .getAllWithOpenStatusByFamilyHouseIdOnlyDateTimesDto(
                        1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentTenantUserDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableDto> expectedTimetableList
                = List.of(mockedRentTimetableDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllByUserIdOfTenant(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableDto> result
                = familyHouseAnnouncementRentTimetableService.getAllOfCurrentTenantUserDto(null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentTenantUserByFamilyHouseAnnouncementIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllByUserIdOfTenantAndFamilyHouseAnnouncementId(eq(1L), eq(2L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = familyHouseAnnouncementRentTimetableService.getAllOfCurrentTenantUserByFamilyHouseAnnouncementIdDto(
                        2L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentUserByFamilyHouseAnnouncementIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementRentTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        eq(2L), eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = familyHouseAnnouncementRentTimetableService.getAllOfCurrentUserByFamilyHouseAnnouncementIdDto(
                2L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalled_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetable() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);
        
        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        familyHouseAnnouncementRentTimetableService.addByFamilyHouseAnnouncementIdWithoutPay(
                mockedRequestRentTimetableWithUserIdOfTenantDto, 1L);


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, times(1))
                .create(testTimetable);
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalledInCaseSpecificIntervalNotDaily_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndValidateIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 2, 18, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        assertThrows(SpecificIntervalIsNotDailyException.class, () -> familyHouseAnnouncementRentTimetableService
                .addByFamilyHouseAnnouncementIdWithoutPay(
                mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalledInCaseSpecificIntervalNotMonthly_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndValidateIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 20, 14, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.MONTHLY_RENT);



        assertThrows(SpecificIntervalIsNotMonthlyException.class, () -> familyHouseAnnouncementRentTimetableService
                .addByFamilyHouseAnnouncementIdWithoutPay(
                        mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalledInCaseSpecificIntervalIsBusy_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndValidateIntervalAndCheckIntervalOnBusyAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        assertThrows(InSpecificIntervalExistRecordsException.class, () -> familyHouseAnnouncementRentTimetableService
                .addByFamilyHouseAnnouncementIdWithoutPay(
                        mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUserCalled_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndCreateTimetableAndApplyOperationAndCreatePurchase() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedFamilyHouseAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        familyHouseAnnouncementRentTimetableService.addByFamilyHouseAnnouncementIdAndPayFromCurrentTenantUser(
                mockedRequestRentTimetableDto, 1L);


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, times(1))
                .create(testTimetable);

        ArgumentCaptor<BalanceOperation> balanceOperationArgumentCaptor
                = ArgumentCaptor.forClass(BalanceOperation.class);

        verify(mockedBalanceOperationService, times(1))
                .addFromCurrentUserAndApplyOperation(balanceOperationArgumentCaptor.capture());

        assertEquals(expectedBalanceOperation.getSum(),
                balanceOperationArgumentCaptor.getValue().getSum());


        ArgumentCaptor<FamilyHouseAnnouncementRentPurchase> purchaseArgumentCaptor
                = ArgumentCaptor.forClass(FamilyHouseAnnouncementRentPurchase.class);

        verify(mockedFamilyHouseAnnouncementRentPurchaseRepository, times(1))
                .create(purchaseArgumentCaptor.capture());

        assertEquals(
                testTimetable,
                purchaseArgumentCaptor.getValue().getTimetable()
        );
        assertEquals(
                expectedBalanceOperation.getSum(),
                purchaseArgumentCaptor.getValue().getBalanceOperation().getSum()
        );
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsNotDaily_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 2, 18, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedFamilyHouseAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(SpecificIntervalIsNotDailyException.class, () -> familyHouseAnnouncementRentTimetableService
                .addByFamilyHouseAnnouncementIdAndPayFromCurrentTenantUser(
                        mockedRequestRentTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedFamilyHouseAnnouncementRentPurchaseRepository, never())
                .create(any());

    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsNotMonthly_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 20, 14, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.MONTHLY_RENT);

        when(mockedFamilyHouseAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(SpecificIntervalIsNotMonthlyException.class, () -> familyHouseAnnouncementRentTimetableService
                .addByFamilyHouseAnnouncementIdAndPayFromCurrentTenantUser(
                        mockedRequestRentTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedFamilyHouseAnnouncementRentPurchaseRepository, never())
                .create(any());

    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsBusy_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testTimetable = new FamilyHouseAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedFamilyHouseAnnouncement);

        // test exising familyHouse announcement rent timetable
        FamilyHouseAnnouncementRentTimetable testExistingTimetable1 = new FamilyHouseAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementRentTimetable(mockedRequestRentTimetableDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedFamilyHouseAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedFamilyHouseAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(InSpecificIntervalExistRecordsException.class, () -> familyHouseAnnouncementRentTimetableService.addByFamilyHouseAnnouncementIdAndPayFromCurrentTenantUser(
                mockedRequestRentTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedFamilyHouseAnnouncementRentPurchaseRepository, never())
                .create(any());

    }
    
    
}
