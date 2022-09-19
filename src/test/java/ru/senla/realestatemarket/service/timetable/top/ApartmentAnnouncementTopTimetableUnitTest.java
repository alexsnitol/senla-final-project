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
import ru.senla.realestatemarket.mapper.timetable.top.ApartmentAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.dictionary.AnnouncementTopPrice;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;
import ru.senla.realestatemarket.model.purchase.top.ApartmentAnnouncementTopPurchase;
import ru.senla.realestatemarket.model.timetable.top.ApartmentAnnouncementTopTimetable;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.purchase.top.IApartmentAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;
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
class ApartmentAnnouncementTopTimetableUnitTest {

    @Autowired
    IApartmentAnnouncementTopTimetableService apartmentAnnouncementTopTimetableService;

    @Autowired
    IApartmentAnnouncementTopTimetableRepository mockedApartmentAnnouncementTopTimetableRepository;
    @Autowired
    IApartmentAnnouncementRepository mockedApartmentAnnouncementRepository;
    @Autowired
    IApartmentAnnouncementTopPurchaseRepository mockedApartmentAnnouncementTopPurchaseRepository;
    @Autowired
    IAnnouncementTopPriceRepository mockedAnnouncementTopPriceRepository;
    @Autowired
    ApartmentAnnouncementTopTimetableMapper mockedTimetableMapper;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    IBalanceOperationService mockedBalanceOperationService;
    @Autowired
    IUserRepository mockedUserRepository;

    TopTimetableWithoutAnnouncementIdDto mockedTopTimetableWithoutAnnouncementIdDto
            = mock(TopTimetableWithoutAnnouncementIdDto.class);
    ApartmentAnnouncementTopTimetable mockedApartmentAnnouncementTopTimetable
            = mock(ApartmentAnnouncementTopTimetable.class);
    RequestTopTimetableDto mockedRequestTopTimetableDto
            = mock(RequestTopTimetableDto.class);
    ApartmentAnnouncement mockedApartmentAnnouncement
            = mock(ApartmentAnnouncement.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedApartmentAnnouncementTopTimetableRepository,
                mockedAnnouncementTopPriceRepository,
                mockedApartmentAnnouncement,
                mockedApartmentAnnouncementTopTimetable,
                mockedApartmentAnnouncementRepository,
                mockedApartmentAnnouncementTopPurchaseRepository,
                mockedRequestTopTimetableDto,
                mockedUserUtil,
                mockedUserRepository,
                mockedTimetableMapper,
                mockedTopTimetableWithoutAnnouncementIdDto,
                mockedBalanceOperationService
        );
    }


    @Test
    void whenGetAllByApartmentIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementTopTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementId(eq(1L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = apartmentAnnouncementTopTimetableService
                .getAllByApartmentIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }

    @Test
    void whenGetAllOfCurrentUserByApartmentIdDtoCalled_ThenFindItAllAndReturnUnmappedTimetables() {
        // test timetable list
        List<ApartmentAnnouncementTopTimetable> testTimetableList
                = List.of(mockedApartmentAnnouncementTopTimetable);

        // expected timetable list
        List<TopTimetableWithoutAnnouncementIdDto> expectedTimetableList
                = List.of(mockedTopTimetableWithoutAnnouncementIdDto);

        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(2L);
        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndUserIdOfOwnerInPropertyItAnnouncement(
                        eq(1L), eq(2L), any(), any()))
                .thenReturn(testTimetableList);
        when(mockedTimetableMapper
                .toTopTimetableWithoutAnnouncementIdDtoFromApartmentAnnouncementTopTimetable(testTimetableList))
                .thenReturn(expectedTimetableList);


        List<TopTimetableWithoutAnnouncementIdDto> result = apartmentAnnouncementTopTimetableService
                .getAllOfCurrentUserByApartmentIdDto(1L, null, null);


        assertEquals(expectedTimetableList, result);
    }
    
    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalled_ThenFindApartmentAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetable() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);
        
        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        ApartmentAnnouncementTopTimetable testExistingTimetable2 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        
        ApartmentAnnouncementTopTimetable testExistingTimetable3 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));
        
        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);
        
        // expected apartment announcement top timetable list after converting
        ApartmentAnnouncementTopTimetable expectedTimetable1 = new ApartmentAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedApartmentAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        ApartmentAnnouncementTopTimetable expectedTimetable2 = new ApartmentAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedApartmentAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<ApartmentAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
        expectedTimetableList.add(expectedTimetable1);
        expectedTimetableList.add(expectedTimetable2);
        
        
        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        apartmentAnnouncementTopTimetableService.addByApartmentAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<ApartmentAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(ApartmentAnnouncementTopTimetable.class);

        verify(mockedApartmentAnnouncementTopTimetableRepository, times(2))
                .create(timetableArgumentCaptor.capture());

        for (int i = 0; i < expectedTimetableList.size(); i++) {
            assertEquals(expectedTimetableList.get(i), timetableArgumentCaptor.getAllValues().get(i));
        }
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalledInCaseAllTimeIsBusy_ThenFindApartmentAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> apartmentAnnouncementTopTimetableService
                .addByApartmentAnnouncementIdWithoutPay(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedApartmentAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithoutPayCalledInCaseSpecificTimeIsBusy_ThenFindApartmentAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);


        // test
        when(mockedApartmentAnnouncementRepository
                .findById(1L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);


        assertThrows(SpecificIntervalFullyBusyException.class, () -> apartmentAnnouncementTopTimetableService
                .addByApartmentAnnouncementIdWithoutPay(mockedRequestTopTimetableDto, 1L));


        verify(mockedApartmentAnnouncementTopTimetableRepository, never()).create(any());
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentUserCalled_ThenFindApartmentAnnouncementAndUnmappedRequestAndForEveryIntervalCreatedTimetableAndApplyOperationsAndCreatePurchases() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 16, 0, 0);

        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.APARTMENT);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        ApartmentAnnouncementTopTimetable testExistingTimetable2 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));
        testExistingTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));

        ApartmentAnnouncementTopTimetable testExistingTimetable3 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable3.setFromDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));
        testExistingTimetable3.setToDt(LocalDateTime.of(2022, 1, 1, 17, 0, 0));

        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);
        testExistingTimetableList.add(testExistingTimetable2);
        testExistingTimetableList.add(testExistingTimetable3);

        // expected apartment announcement top timetable list after converting
        ApartmentAnnouncementTopTimetable expectedTimetable1 = new ApartmentAnnouncementTopTimetable();
        expectedTimetable1.setAnnouncement(mockedApartmentAnnouncement);
        expectedTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));
        expectedTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 12, 0, 0));

        ApartmentAnnouncementTopTimetable expectedTimetable2 = new ApartmentAnnouncementTopTimetable();
        expectedTimetable2.setAnnouncement(mockedApartmentAnnouncement);
        expectedTimetable2.setFromDt(LocalDateTime.of(2022, 1, 1, 13, 0, 0));
        expectedTimetable2.setToDt(LocalDateTime.of(2022, 1, 1, 15, 0, 0));

        List<ApartmentAnnouncementTopTimetable> expectedTimetableList = new LinkedList<>();
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

        when(mockedApartmentAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.SELL);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.APARTMENT, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        apartmentAnnouncementTopTimetableService.addByApartmentAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L);


        ArgumentCaptor<ApartmentAnnouncementTopTimetable> timetableArgumentCaptor
                = ArgumentCaptor.forClass(ApartmentAnnouncementTopTimetable.class);

        verify(mockedApartmentAnnouncementTopTimetableRepository, times(2))
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


        ArgumentCaptor<ApartmentAnnouncementTopPurchase> purchaseArgumentCaptor
                = ArgumentCaptor.forClass(ApartmentAnnouncementTopPurchase.class);

        verify(mockedApartmentAnnouncementTopPurchaseRepository, times(2))
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
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentUserCalledInCaseAllTimeIsBusy_ThenFindApartmentAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 10, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.APARTMENT);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedApartmentAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(false);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.SELL);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.APARTMENT, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        assertThrows(SpecificIntervalFullyBusyException.class, () -> apartmentAnnouncementTopTimetableService
                .addByApartmentAnnouncementIdWithPayFromCurrentUser(
                mockedRequestTopTimetableDto, 1L));


        verify(mockedApartmentAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedApartmentAnnouncementTopPurchaseRepository, never())
                .create(any());
    }

    @Test
    void whenAddByApartmentAnnouncementIdWithPayFromCurrentUserCalledInCaseSpecificTimeIsBusy_ThenFindApartmentAnnouncementAndCheckFreeIntervalsAndThrownSpecificIntervalFullyBusyException() {
        // test date times
        LocalDateTime testFromDt = LocalDateTime.of(2022, 1, 1, 9, 0, 0);
        LocalDateTime testToDt = LocalDateTime.of(2022, 1, 1, 11, 0, 0);

        // test apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testTimetable = new ApartmentAnnouncementTopTimetable();
        testTimetable.setFromDt(testFromDt);
        testTimetable.setToDt(testToDt);

        // test announcement top price
        AnnouncementTopPrice testAnnouncementTopPrice = new AnnouncementTopPrice();
        testAnnouncementTopPrice.setPropertyType(PropertyTypeEnum.APARTMENT);
        testAnnouncementTopPrice.setAnnouncementType(AnnouncementTypeEnum.SELL);
        testAnnouncementTopPrice.setPricePerHour(100F);

        // test user
        User testMockedUser = mock(User.class);

        // test exising apartment announcement top timetable
        ApartmentAnnouncementTopTimetable testExistingTimetable1 = new ApartmentAnnouncementTopTimetable();
        testExistingTimetable1.setFromDt(LocalDateTime.of(2022, 1, 1, 9, 0, 0));
        testExistingTimetable1.setToDt(LocalDateTime.of(2022, 1, 1, 11, 0, 0));

        List<ApartmentAnnouncementTopTimetable> testExistingTimetableList = new LinkedList<>();
        testExistingTimetableList.add(testExistingTimetable1);

        // test
        when(mockedUserUtil
                .getCurrentUserId())
                .thenReturn(2L);

        when(mockedApartmentAnnouncementRepository
                .findByIdAndUserIdOfOwnerInProperty(1L, 2L))
                .thenReturn(mockedApartmentAnnouncement);

        when(mockedTimetableMapper
                .toApartmentAnnouncementTopTimetable(mockedRequestTopTimetableDto))
                .thenReturn(testTimetable);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .isExist(any(Specification.class)))
                .thenReturn(true);

        when(mockedApartmentAnnouncement.getId())
                .thenReturn(1L);

        when(mockedApartmentAnnouncement
                .getType())
                .thenReturn(HousingAnnouncementTypeEnum.SELL);

        when(mockedApartmentAnnouncementTopTimetableRepository
                .findAllByApartmentAnnouncementIdAndConcernsTheIntervalBetweenSpecificFromAndTo(
                        1L, testFromDt, testToDt, Sort.by(Sort.Direction.ASC, "fromDt")))
                .thenReturn(testExistingTimetableList);

        when(mockedAnnouncementTopPriceRepository
                .findPriceByPropertyTypeAndAnnouncementType(PropertyTypeEnum.APARTMENT, AnnouncementTypeEnum.SELL))
                .thenReturn(testAnnouncementTopPrice);

        when(mockedUserRepository.findById(2L))
                .thenReturn(testMockedUser);

        when(testMockedUser
                .getBalance())
                .thenReturn(300D);



        assertThrows(SpecificIntervalFullyBusyException.class, () -> apartmentAnnouncementTopTimetableService
                .addByApartmentAnnouncementIdWithPayFromCurrentUser(
                        mockedRequestTopTimetableDto, 1L));


        verify(mockedApartmentAnnouncementTopTimetableRepository, never())
                .create(any());

        verify(mockedBalanceOperationService, never())
                .addFromCurrentUserAndApplyOperation(any(BalanceOperation.class));

        verify(mockedApartmentAnnouncementTopPurchaseRepository, never())
                .create(any());
    }


}
