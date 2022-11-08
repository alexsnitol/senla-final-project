package ru.senla.realestatemarket.service.timetable.top;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.senla.realestatemarket.dto.timetable.RequestTopTimetableDto;
import ru.senla.realestatemarket.dto.timetable.TopTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.exception.SpecificIntervalFullyBusyException;
import ru.senla.realestatemarket.mapper.timetable.top.FamilyHouseAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.FamilyHouseAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.FamilyHouseAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.IFamilyHouseAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.timetable.top.impl.FamilyHouseAnnouncementTopTimetableServiceImpl;
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

@ExtendWith({MockitoExtension.class})
class FamilyHouseAnnouncementTopTimetableUnitTest {

    @InjectMocks FamilyHouseAnnouncementTopTimetableServiceImpl familyHouseAnnouncementTopTimetableService;

    @Mock IFamilyHouseAnnouncementTopTimetableRepository mockedFamilyHouseAnnouncementTopTimetableRepository;
    @Mock IFamilyHouseAnnouncementRepository mockedFamilyHouseAnnouncementRepository;
    @Mock IFamilyHouseAnnouncementTopPurchaseRepository mockedFamilyHouseAnnouncementTopPurchaseRepository;
    @Mock IAnnouncementTopPriceRepository mockedAnnouncementTopPriceRepository;
    @Mock FamilyHouseAnnouncementTopTimetableMapper mockedTimetableMapper;
    @Mock UserUtil mockedUserUtil;
    @Mock IBalanceOperationService mockedBalanceOperationService;
    @Mock IUserRepository mockedUserRepository;

    @Mock TopTimetableWithoutAnnouncementIdDto mockedTopTimetableWithoutAnnouncementIdDto;
    @Mock FamilyHouseAnnouncementTopTimetable mockedFamilyHouseAnnouncementTopTimetable;
    @Mock RequestTopTimetableDto mockedRequestTopTimetableDto;
    @Mock FamilyHouseAnnouncement mockedFamilyHouseAnnouncement;


    @Test
    void whenGetAllByFamilyHouseIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementTopTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = familyHouseAnnouncementTopTimetableService
                .getAllByFamilyHouseIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentUserByFamilyHouseIdDto_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<FamilyHouseAnnouncementTopTimetable> testTimetableList
                = List.of(mockedFamilyHouseAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(2L);
        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndUserIdOfOwnerInPropertyOfAnnouncementById(
                        eq(1L), eq(2L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromFamilyHouseAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = familyHouseAnnouncementTopTimetableService
                .getAllOfCurrentUserByFamilyHouseIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }
    
    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalled_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetable() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);
        
        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        FamilyHouseAnnouncementTopTimetable testExistingTimetable2 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        
        FamilyHouseAnnouncementTopTimetable testExistingTimetable3 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));
        
        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);
        
        // expected familyHouse announcement top timetable list after converting
        FamilyHouseAnnouncementTopTimetable expectedTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedFamilyHouseAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        FamilyHouseAnnouncementTopTimetable expectedTimetable2 = new FamilyHouseAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedFamilyHouseAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
        expectedTimetableList.add(expectedTimetable1);
        expectedTimetableList.add(expectedTimetable2);
        
        
        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        familyHouseAnnouncementTopTimetableService.addByFamilyHouseAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<FamilyHouseAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(FamilyHouseAnnouncementTopTimetable.class);

        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, times(2))
                .create(timetableArgumentCaptor.capture());

        for (int i = 0; i < expectedTimetableList.size(); i++) {
            assertEquals(expectedTimetableList.get(i), timetableArgumentCaptor.getAllValues().get(i));
        }
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalledInCaseAllTimeIsBusy_ThenFindFamilyHouseAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> familyHouseAnnouncementTopTimetableService
                .addByFamilyHouseAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithoutPayCalledInCaseSpecificTimeIsBusy_ThenFindFamilyHouseAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedFamilyHouseAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> familyHouseAnnouncementTopTimetableService
                .addByFamilyHouseAnnouncementIdWithoutPay(mockedRequestTopTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentUserCalled_ThenFindFamilyHouseAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetableAndApplyOperationsAndCreatePurchases() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);

        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        FamilyHouseAnnouncementTopTimetable testExistingTimetable2 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));

        FamilyHouseAnnouncementTopTimetable testExistingTimetable3 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);

        // expected familyHouse announcement top timetable list after converting
        FamilyHouseAnnouncementTopTimetable expectedTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedFamilyHouseAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        FamilyHouseAnnouncementTopTimetable expectedTimetable2 = new FamilyHouseAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedFamilyHouseAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
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

        when(mockedFamilyHouseAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.SELL);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.FAMILY_HOUSE, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        familyHouseAnnouncementTopTimetableService.addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<FamilyHouseAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(FamilyHouseAnnouncementTopTimetable.class);

        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, times(2))
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


        ArgumentCaptor<FamilyHouseAnnouncementTopPurchase> purchaseArgumentCaptor
                = ArgumentCaptor.forClass(FamilyHouseAnnouncementTopPurchase.class);

        verify(mockedFamilyHouseAnnouncementTopPurchaseRepository, times(2))
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
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentUserCalledInCaseAllTimeIsBusy_ThenFindFamilyHouseAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedFamilyHouseAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedFamilyHouseAnnouncement.getId())
                .thenReturn(1L);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .findAllByFamilyHouseAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);



        assertThrows(SpecificIntervalFullyBusyException.class, () -> familyHouseAnnouncementTopTimetableService
                .addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedFamilyHouseAnnouncementTopPurchaseRepository, never())
                .create(any());
    }

    @Test
    void whenAddByFamilyHouseAnnouncementIdWithPayFromCurrentUserCalledInCaseSpecificTimeIsBusy_ThenFindFamilyHouseAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testTimetable = new FamilyHouseAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising familyHouse announcement top timetable
        FamilyHouseAnnouncementTopTimetable testExistingTimetable1 = new FamilyHouseAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<FamilyHouseAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedFamilyHouseAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedFamilyHouseAnnouncement);

        when(mockedTimetableMapper
                .toFamilyHouseAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedFamilyHouseAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> familyHouseAnnouncementTopTimetableService
                .addByFamilyHouseAnnouncementIdWithPayFromCurrentUser(
                        mockedRequestTopTimetableDto, 1L));


        verify(mockedFamilyHouseAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedFamilyHouseAnnouncementTopPurchaseRepository, never())
                .create(any());
    }


}
