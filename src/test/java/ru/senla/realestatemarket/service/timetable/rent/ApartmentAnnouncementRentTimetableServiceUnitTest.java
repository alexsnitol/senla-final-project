package ru.senla.realestatemarket.service.timetable.rent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.senla.realestatemarket.mapper.timetable.rent.ApartmentAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.purchase.rent.ApartmentAnnouncementRentPurchase;
import ru.senla.realestatemarket.model.timetable.rent.ApartmentAnnouncementRentTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IApartmentAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class ApartmentAnnouncementRentTimetableServiceUnitTest {

    @Autowired
    IApartmentAnnouncementRentTimetableService apartmentAnnouncementRentTimetableService;

    @Autowired
    IApartmentAnnouncementRentTimetableRepository mockedApartmentAnnouncementRentTimetableRepository;
    @Autowired
    IApartmentAnnouncementRepository mockedApartmentAnnouncementRepository;
    @Autowired
    IApartmentAnnouncementRentPurchaseRepository mockedApartmentAnnouncementRentPurchaseRepository;

    @Autowired
    ApartmentAnnouncementRentTimetableMapper mockedTimetableMapper;

    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    IBalanceOperationService mockedBalanceOperationService;


    RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto
            = mock(RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto.class);
    ApartmentAnnouncementRentTimetable mockedApartmentAnnouncementRentTimetable
            = mock(ApartmentAnnouncementRentTimetable.class);
    RentTimetableWithoutAnnouncementIdDto mockedRentTimetableWithoutAnnouncementIdDto
            = mock(RentTimetableWithoutAnnouncementIdDto.class);
    RequestRentTimetableWithUserIdOfTenantDto mockedRequestRentTimetableWithUserIdOfTenantDto
            = mock(RequestRentTimetableWithUserIdOfTenantDto.class);
    RequestRentTimetableDto mockedRequestRentTimetableDto
            = mock(RequestRentTimetableDto.class);
    RentTimetableDto mockedRentTimetableDto
            = mock(RentTimetableDto.class);
    ApartmentAnnouncement mockedApartmentAnnouncement
            = mock(ApartmentAnnouncement.class);
    User mockedUser = mock(User.class);



    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedApartmentAnnouncementRentTimetableRepository,
                mockedApartmentAnnouncementRepository,
                mockedApartmentAnnouncementRentPurchaseRepository,
                mockedTimetableMapper,
                mockedUserRepository,
                mockedUserUtil,
                mockedBalanceOperationService,
                mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto,
                mockedApartmentAnnouncementRentTimetable,
                mockedRentTimetableWithoutAnnouncementIdDto,
                mockedRentTimetableDto,
                mockedUser,
                mockedApartmentAnnouncement
        );
    }


    @Test
    void whenGetAllByApartmentIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto);

        // test
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllByApartmentAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDtoFromApartmentAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> result
                = apartmentAnnouncementRentTimetableService
                .getAllByApartmentIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllByApartmentIdOnlyDateTimesDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllByApartmentAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = apartmentAnnouncementRentTimetableService
                .getAllByApartmentIdOnlyDateTimesDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllWithOpenStatusByApartmentIdOnlyDateTimesDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllWithOpenStatusByApartmentAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = apartmentAnnouncementRentTimetableService
                .getAllWithOpenStatusByApartmentIdOnlyDateTimesDto(
                        1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentTenantUserDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableDto> expectedTimetableList
                = List.of(mockedRentTimetableDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllByUserIdOfTenant(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableDtoFromApartmentAnnouncementTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableDto> result
                = apartmentAnnouncementRentTimetableService.getAllOfCurrentTenantUserDto(null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentTenantUserByApartmentAnnouncementIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllByUserIdOfTenantAndApartmentAnnouncementId(eq(1L), eq(2L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = apartmentAnnouncementRentTimetableService.getAllOfCurrentTenantUserByApartmentAnnouncementIdDto(
                        2L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentUserByApartmentAnnouncementIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementRentTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementRentTimetable);

        // expected timetable list
        List<RentTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedRentTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(1L);
        when(mockedApartmentAnnouncementRentTimetableRepository
                .findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        eq(2L), eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toRentTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementRentTimetable(
                        testTimetableList))
                .thenReturn(expectedTimetableList);


        List<RentTimetableWithoutAnnouncementIdDto> result
                = apartmentAnnouncementRentTimetableService.getAllOfCurrentUserByApartmentAnnouncementIdDto(
                2L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalled_ThenFindApartmentAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetable() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);
        
        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        apartmentAnnouncementRentTimetableService.addByApartmentAnnouncementIdWithoutPay(
                mockedRequestRentTimetableWithUserIdOfTenantDto, 1L);


        verify(mockedApartmentAnnouncementRentTimetableRepository, times(1))
                .create(testTimetable);
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalledInCaseSpecificIntervalNotDaily_ThenFindApartmentAnnouncementAndUnmappedRequestAndValidateIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 2, 18, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        assertThrows(SpecificIntervalIsNotDailyException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdWithoutPay(
                mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalledInCaseSpecificIntervalNotMonthly_ThenFindApartmentAnnouncementAndUnmappedRequestAndValidateIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 20, 14, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.MONTHLY_RENT);



        assertThrows(SpecificIntervalIsNotMonthlyException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdWithoutPay(
                        mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalledInCaseSpecificIntervalIsBusy_ThenFindApartmentAnnouncementAndUnmappedRequestAndValidateIntervalAndCheckIntervalOnBusyAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableWithUserIdOfTenantDto))
                .thenReturn(testTimetable);

        when(mockedRequestRentTimetableWithUserIdOfTenantDto
                .getUserIdOfTenant())
                .thenReturn(2L);

        when(mockedUserRepository
                .findById(2L))
                .thenReturn(mockedUser);

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);



        assertThrows(InSpecificIntervalExistRecordsException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdWithoutPay(
                        mockedRequestRentTimetableWithUserIdOfTenantDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(testTimetable);
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentTenantUserCalled_ThenFindApartmentAnnouncementAndUnmappedRequestAndCreateTimetableAndApplyOperationAndCreatePurchase() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableDto))
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

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedApartmentAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        apartmentAnnouncementRentTimetableService.addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
                mockedRequestRentTimetableDto, 1L);


        verify(mockedApartmentAnnouncementRentTimetableRepository, times(1))
                .create(testTimetable);

        ArgumentCaptor<BalanceOperation> balanceOperationArgumentCaptor
                = ArgumentCaptor.forClass(BalanceOperation.class);

        verify(mockedBalanceOperationService, times(1))
                .addFromCurrentUserAndApplyOperation(balanceOperationArgumentCaptor.capture());

        assertEquals(expectedBalanceOperation.getSum(),
                balanceOperationArgumentCaptor.getValue().getSum());


        ArgumentCaptor<ApartmentAnnouncementRentPurchase> purchaseArgumentCaptor
                = ArgumentCaptor.forClass(ApartmentAnnouncementRentPurchase.class);

        verify(mockedApartmentAnnouncementRentPurchaseRepository, times(1))
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
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsNotDaily_ThenFindApartmentAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 2, 18, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableDto))
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

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedApartmentAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(SpecificIntervalIsNotDailyException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
                        mockedRequestRentTimetableDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedApartmentAnnouncementRentPurchaseRepository, never())
                .create(any());

    }

    @Test
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsNotMonthly_ThenFindApartmentAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 20, 14, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableDto))
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

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.MONTHLY_RENT);

        when(mockedApartmentAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(SpecificIntervalIsNotMonthlyException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
                        mockedRequestRentTimetableDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedApartmentAnnouncementRentPurchaseRepository, never())
                .create(any());

    }

    @Test
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentTenantUserCalledInCaseSpecificIntervalIsBusy_ThenFindApartmentAnnouncementAndUnmappedRequestAndCheckIntervalAndThrowException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 2, 14, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 3, 14, 0, 0);

        // test apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testTimetable = new ApartmentAnnouncementRentTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);
        testTimetable.setTenant(mockedUser);
        testTimetable.setAnnouncement(mockedApartmentAnnouncement);

        // test exising apartment announcement rent timetable
        ApartmentAnnouncementRentTimetable testExistingTimetable1 = new ApartmentAnnouncementRentTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 14, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 2, 14, 0, 0));

        // expected balance operation
        BalanceOperation expectedBalanceOperation = new BalanceOperation();
        expectedBalanceOperation.setSum(-100D);

        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementRentTimetable(mockedRequestRentTimetableDto))
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

        when(mockedApartmentAnnouncementRentTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.DAILY_RENT);

        when(mockedApartmentAnnouncement
                .getPrice())
                .thenReturn(100D);

        when(mockedUser
                .getBalance())
                .thenReturn(100D);



        assertThrows(InSpecificIntervalExistRecordsException.class, () -> apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
                mockedRequestRentTimetableDto, 1L));


        verify(mockedApartmentAnnouncementRentTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedApartmentAnnouncementRentPurchaseRepository, never())
                .create(any());

    }
    
    
}
