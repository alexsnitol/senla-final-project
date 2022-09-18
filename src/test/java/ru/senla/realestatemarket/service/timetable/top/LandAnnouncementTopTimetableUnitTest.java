package ru.senla.realestatemarket.service.timetable.top;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.exception.SpecificIntervalFullyBusyException;
import ru.senla.realestatemarket.mapper.timetable.top.LandAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.announcement.NonHousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.LandAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.LandAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.ILandAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
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

@ExtendWith({SpringExtension.class})
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class LandAnnouncementTopTimetableUnitTest {

    @Autowired
    ILandAnnouncementTopTimetableService landAnnouncementTopTimetableService;

    @Autowired
    ILandAnnouncementTopTimetableRepository mockedLandAnnouncementTopTimetableRepository;
    @Autowired
    ILandAnnouncementRepository mockedLandAnnouncementRepository;
    @Autowired
    ILandAnnouncementTopPurchaseRepository mockedLandAnnouncementTopPurchaseRepository;
    @Autowired
    IAnnouncementTopPriceRepository mockedAnnouncementTopPriceRepository;
    @Autowired
    LandAnnouncementTopTimetableMapper mockedTimetableMapper;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    IBalanceOperationService mockedBalanceOperationService;
    @Autowired
    IUserRepository mockedUserRepository;

    TopTimetableWithoutAnnouncementIdDto mockedTopTimetableWithoutAnnouncementIdDto
            = mock(TopTimetableWithoutAnnouncementIdDto.class);
    LandAnnouncementTopTimetable mockedLandAnnouncementTopTimetable
            = mock(LandAnnouncementTopTimetable.class);
    RequestTopTimetableDto mockedRequestTopTimetableDto
            = mock(RequestTopTimetableDto.class);
    LandAnnouncement mockedLandAnnouncement
            = mock(LandAnnouncement.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedLandAnnouncementTopTimetableRepository,
                mockedAnnouncementTopPriceRepository,
                mockedLandAnnouncement,
                mockedLandAnnouncementTopTimetable,
                mockedLandAnnouncementRepository,
                mockedLandAnnouncementTopPurchaseRepository,
                mockedRequestTopTimetableDto,
                mockedUserUtil,
                mockedUserRepository,
                mockedTimetableMapper,
                mockedTopTimetableWithoutAnnouncementIdDto,
                mockedBalanceOperationService
        );
    }


    @Test
    void whenGetAllByLandIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<LandAnnouncementTopTimetable> testTimetableList
                = List.of(mockedLandAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedLandAnnouncementTopTimetableRepository
                .findAllByLandAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromLandAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = landAnnouncementTopTimetableService
                .getAllByLandIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentUserByLandIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<LandAnnouncementTopTimetable> testTimetableList
                = List.of(mockedLandAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(2L);
        when(mockedLandAnnouncementTopTimetableRepository
                .finaAllByLandAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        eq(1L), eq(2L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromLandAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = landAnnouncementTopTimetableService
                .getAllOfCurrentUserByLandIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }
    
    @Test
    void whenAddByLandAnnouncementIdWithoutPayCalled_ThenFindLandAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetable() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);
        
        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        LandAnnouncementTopTimetable testExistingTimetable2 = new LandAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        
        LandAnnouncementTopTimetable testExistingTimetable3 = new LandAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));
        
        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);
        
        // expected land announcement top timetable list after converting
        LandAnnouncementTopTimetable expectedTimetable1 = new LandAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedLandAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        LandAnnouncementTopTimetable expectedTimetable2 = new LandAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedLandAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<LandAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
        expectedTimetableList.add(expectedTimetable1);
        expectedTimetableList.add(expectedTimetable2);
        
        
        // test
        when(mockedLandAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        landAnnouncementTopTimetableService.addByLandAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<LandAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(LandAnnouncementTopTimetable.class);

        verify(mockedLandAnnouncementTopTimetableRepository, times(2))
                .create(timetableArgumentCaptor.capture());

        for (int i = 0; i < expectedTimetableList.size(); i++) {
            assertEquals(expectedTimetableList.get(i), timetableArgumentCaptor.getAllValues().get(i));
        }
    }

    @Test
    void whenAddByLandAnnouncementIdWithoutPayCalledInCaseAllTimeIsBusy_ThenFindLandAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedLandAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> landAnnouncementTopTimetableService
                .addByLandAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedLandAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByLandAnnouncementIdWithoutPayCalledInCaseSpecificTimeIsBusy_ThenFindLandAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedLandAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> landAnnouncementTopTimetableService
                .addByLandAnnouncementIdWithoutPay(mockedRequestTopTimetableDto, 1L));


        verify(mockedLandAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByLandAnnouncementIdWithPayFromCurrentUserCalled_ThenFindLandAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetableAndApplyOperationsAndCreatePurchases() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);

        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.LAND);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        LandAnnouncementTopTimetable testExistingTimetable2 = new LandAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));

        LandAnnouncementTopTimetable testExistingTimetable3 = new LandAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));

        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);

        // expected land announcement top timetable list after converting
        LandAnnouncementTopTimetable expectedTimetable1 = new LandAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedLandAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        LandAnnouncementTopTimetable expectedTimetable2 = new LandAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedLandAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<LandAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
        expectedTimetableList.add(expectedTimetable1);
        expectedTimetableList.add(expectedTimetable2);

        // expected balance operation
        BalanceOperation expectedBalanceOperation1 = new BalanceOperation();
        expectedBalanceOperation1.setSum(-100D);

        BalanceOperation expectedBalanceOperation2 = new BalanceOperation();
        expectedBalanceOperation2.setSum(-200D);

        List<BalanceOperation> expectedBalanceOperationList
                = List.of(expectedBalanceOperation1, expectedBalanceOperation2);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedLandAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncement
                .getType())
                .thenReturn(NonHousingAnnouncementTypeEnum.SELL);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.LAND, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        landAnnouncementTopTimetableService.addByLandAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<LandAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(LandAnnouncementTopTimetable.class);

        verify(mockedLandAnnouncementTopTimetableRepository, times(2))
                .create(timetableArgumentCaptor.capture());

        for (int i = 0; i < expectedTimetableList.size(); i++) {
            assertEquals(expectedTimetableList.get(i), timetableArgumentCaptor.getAllValues().get(i));
        }


        ArgumentCaptor<BalanceOperation> balanceOperationArgumentCaptor
                = ArgumentCaptor.forClass(BalanceOperation.class);

        verify(mockedBalanceOperationService, times(2))
                .addFromCurrentUserAndApplyOperation(balanceOperationArgumentCaptor.capture());

        for (int i = 1; i < expectedBalanceOperationList.size(); i++) {
            assertEquals(expectedBalanceOperationList.get(i).getSum(),
                    balanceOperationArgumentCaptor.getAllValues().get(i).getSum());
        }


        ArgumentCaptor<LandAnnouncementTopPurchase> purchaseArgumentCaptor
                = ArgumentCaptor.forClass(LandAnnouncementTopPurchase.class);

        verify(mockedLandAnnouncementTopPurchaseRepository, times(2))
                .create(purchaseArgumentCaptor.capture());

        for (int i = 0; i < expectedTimetableList.size(); i++) {
            assertEquals(
                    expectedTimetableList.get(i),
                    purchaseArgumentCaptor.getAllValues().get(i).getTimetable()
            );
            assertEquals(
                    expectedBalanceOperationList.get(i).getSum(),
                    purchaseArgumentCaptor.getAllValues().get(i).getBalanceOperation().getSum()
            );
        }

    }

    @Test
    void whenAddByLandAnnouncementIdWithPayFromCurrentUserCalledInCaseAllTimeIsBusy_ThenFindLandAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.LAND);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedLandAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncement
                .getType())
                .thenReturn(NonHousingAnnouncementTypeEnum.SELL);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.LAND, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        assertThrows(SpecificIntervalFullyBusyException.class, () -> landAnnouncementTopTimetableService
                .addByLandAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedLandAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedLandAnnouncementTopPurchaseRepository, never())
                .create(any());
    }

    @Test
    void whenAddByLandAnnouncementIdWithPayFromCurrentUserCalledInCaseSpecificTimeIsBusy_ThenFindLandAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test land announcement top timetable
        LandAnnouncementTopTimetable testTimetable = new LandAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.LAND);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising land announcement top timetable
        LandAnnouncementTopTimetable testExistingTimetable1 = new LandAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<LandAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedLandAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedLandAnnouncement);

        when(mockedTimetableMapper
                .toTopTimetableDtoFromLandAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedLandAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedLandAnnouncement.getId())
                .thenReturn(1L);

        when(mockedLandAnnouncement
                .getType())
                .thenReturn(NonHousingAnnouncementTypeEnum.SELL);

        when(mockedLandAnnouncementTopTimetableRepository
                .findByLandAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.LAND, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        assertThrows(SpecificIntervalFullyBusyException.class, () -> landAnnouncementTopTimetableService
                .addByLandAnnouncementIdWithPayFromCurrentUser(
                        mockedRequestTopTimetableDto, 1L));


        verify(mockedLandAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedLandAnnouncementTopPurchaseRepository, never())
                .create(any());
    }


}
