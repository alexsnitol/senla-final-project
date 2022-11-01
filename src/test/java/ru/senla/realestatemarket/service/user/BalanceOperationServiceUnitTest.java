package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.user.BalanceOperationWithoutUserIdDto;
import ru.senla.realestatemarket.dto.user.RequestBalanceOperationDto;
import ru.senla.realestatemarket.exception.OnSpecificUserNotEnoughMoneyException;
import ru.senla.realestatemarket.mapper.user.BalanceOperationMapper;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IBalanceOperationRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.util.UserUtil;

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
class BalanceOperationServiceUnitTest {

    @Autowired
    IBalanceOperationService balanceOperationService;

    @Autowired
    IBalanceOperationRepository mockedBalanceOperationRepository;
    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    UserUtil mockedUserUtil;
    @Autowired
    BalanceOperationMapper mockedBalanceOperationMapper;

    BalanceOperation mockedBalanceOperation = mock(BalanceOperation.class);
    BalanceOperationWithoutUserIdDto mockedBalanceOperationWithoutUserIdDto = mock(BalanceOperationWithoutUserIdDto.class);
    RequestBalanceOperationDto mockedRequestBalanceOperationDto = mock(RequestBalanceOperationDto.class);
    User mockedUser = mock(User.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedBalanceOperationMapper,
                mockedBalanceOperationRepository,
                mockedUserUtil,
                mockedUserRepository
        );
    }


    @Test
    void whenGetAllByUserIdCalled_ThenFindAllByUserIdAndReturnUnmappedBalanceOperations() {
        // test balance operation list
        List<BalanceOperation> testBalanceOperationList = List.of(mockedBalanceOperation);

        // test balance operation without user id dto list
        List<BalanceOperationWithoutUserIdDto> testBalanceOperationWithoutUserIdDtoList
                = List.of(mockedBalanceOperationWithoutUserIdDto);

        // test balance operation without user id dto list
        List<BalanceOperationWithoutUserIdDto> expectedBalanceOperationWithoutUserIdDtoList
                = List.of(mockedBalanceOperationWithoutUserIdDto);


        // test
        when(mockedBalanceOperationRepository.findAllByUserId(eq(1L), any(), any()))
                .thenReturn(testBalanceOperationList);
        when(mockedBalanceOperationMapper.toBalanceOperationWithoutUserIdDto(testBalanceOperationList))
                .thenReturn(testBalanceOperationWithoutUserIdDtoList);

        List<BalanceOperationWithoutUserIdDto> result
                = balanceOperationService.getAllDtoByUserId(1L, null, null);

        assertEquals(expectedBalanceOperationWithoutUserIdDtoList, result);
    }

    @Test
    void whenGetAllByCurrentUserIdCalled_ThenFindAllByCurrentUserIdAndReturnUnmappedBalanceOperations() {
        // test balance operation list
        List<BalanceOperation> testBalanceOperationList = List.of(mockedBalanceOperation);

        // test balance operation without user id dto list
        List<BalanceOperationWithoutUserIdDto> testBalanceOperationWithoutUserIdDtoList
                = List.of(mockedBalanceOperationWithoutUserIdDto);

        // test balance operation without user id dto list
        List<BalanceOperationWithoutUserIdDto> expectedBalanceOperationWithoutUserIdDtoList
                = List.of(mockedBalanceOperationWithoutUserIdDto);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedBalanceOperationRepository.findAllByUserId(eq(1L), any(), any()))
                .thenReturn(testBalanceOperationList);
        when(mockedBalanceOperationMapper.toBalanceOperationWithoutUserIdDto(testBalanceOperationList))
                .thenReturn(testBalanceOperationWithoutUserIdDtoList);

        List<BalanceOperationWithoutUserIdDto> result
                = balanceOperationService.getAllDtoByUserId(1L, null, null);

        assertEquals(expectedBalanceOperationWithoutUserIdDtoList, result);
    }

    @Test
    void whenAddByUserIdAndApplyOperationCalledInCaseThereIsAnAmountInBalance_ThenFindUserByIdAndCheckHisBalanceAndUpdateBalanceOfUserAndCreateOperation() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        balanceOperationService.addByUserIdAndApplyOperation(testBalanceOperation, 1L);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
        verify(mockedBalanceOperationRepository, times(1)).create(testBalanceOperation);
    }

    @Test
    void whenAddByUserIdAndApplyOperationCalledInCaseThereIsNotAnAmountInBalance_ThenFindUserByIdAndCheckHisBalanceAndThrown() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.addByUserIdAndApplyOperation(testBalanceOperation, 1L));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
        verify(mockedBalanceOperationRepository, never()).create(testBalanceOperation);
    }

    @Test
    void whenAddByUserIdAndApplyOperationCalledWithRequestBalanceOperationDtoParameterInCaseThereIsAnAmountInBalance_ThenFindUserByIdAndCheckHisBalanceAndUpdateBalanceOfUserAndCreateOperation() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        when(mockedBalanceOperationMapper.toBalanceOperation(mockedRequestBalanceOperationDto)).thenReturn(testBalanceOperation);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        balanceOperationService.addByUserIdAndApplyOperation(mockedRequestBalanceOperationDto, 1L);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
        verify(mockedBalanceOperationRepository, times(1)).create(testBalanceOperation);
    }

    @Test
    void whenAddByUserIdAndApplyOperationCalledWithRequestBalanceOperationDtoParameterInCaseThereIsNotAnAmountInBalance_ThenFindUserByIdAndCheckHisBalanceAndThrown() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        when(mockedBalanceOperationMapper.toBalanceOperation(mockedRequestBalanceOperationDto)).thenReturn(testBalanceOperation);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.addByUserIdAndApplyOperation(mockedRequestBalanceOperationDto, 1L));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
        verify(mockedBalanceOperationRepository, never()).create(testBalanceOperation);
    }

    @Test
    void whenAddByCurrentUserIdAndApplyOperationCalledInCaseThereIsAnAmountInBalance_ThenFindUserByCurrentUserIdAndCheckHisBalanceAndUpdateBalanceOfUserAndCreateOperation() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        balanceOperationService.addFromCurrentUserAndApplyOperation(testBalanceOperation);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
        verify(mockedBalanceOperationRepository, times(1)).create(testBalanceOperation);
    }

    @Test
    void whenAddByCurrentUserIdAndApplyOperationCalledInCaseThereIsNotAnAmountInBalance_ThenFindUserByCurrentUserIdAndCheckHisBalanceAndThrown() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.addFromCurrentUserAndApplyOperation(testBalanceOperation));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
        verify(mockedBalanceOperationRepository, never()).create(testBalanceOperation);
    }

    @Test
    void whenAddByCurrentUserIdAndApplyOperationCalledWithRequestBalanceOperationDtoParameterInCaseThereIsAnAmountInBalance_ThenFindUserByCurrentIdAndCheckHisBalanceAndUpdateBalanceOfUserAndCreateOperation() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        when(mockedBalanceOperationMapper.toBalanceOperation(mockedRequestBalanceOperationDto)).thenReturn(testBalanceOperation);
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        balanceOperationService.addByUserIdAndApplyOperation(mockedRequestBalanceOperationDto, 1L);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
        verify(mockedBalanceOperationRepository, times(1)).create(testBalanceOperation);
    }

    @Test
    void whenAddByCurrentUserIdAndApplyOperationCalledWithRequestBalanceOperationDtoParameterInCaseThereIsNotAnAmountInBalance_ThenFindUserByCurrentUserIdAndCheckHisBalanceAndThrown() {
        // test balance operation
        BalanceOperation testBalanceOperation = new BalanceOperation();
        testBalanceOperation.setSum(-100D);

        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        when(mockedBalanceOperationMapper.toBalanceOperation(mockedRequestBalanceOperationDto)).thenReturn(testBalanceOperation);
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.addByUserIdAndApplyOperation(mockedRequestBalanceOperationDto, 1L));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
        verify(mockedBalanceOperationRepository, never()).create(testBalanceOperation);
    }

    @Test
    void whenApplyOperationToUserBalanceCalledInCaseThereIsAnAmountInBalance_ThenCheckHisBalanceAndUpdateBalanceOfUser() {
        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        balanceOperationService.applyOperationToUserBalance(testUser, -100D);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
    }

    @Test
    void whenApplyOperationToUserBalanceCalledInCaseThereIsNotAnAmountInBalance_ThenCheckHisBalanceAndThrown() {
        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.applyOperationToUserBalance(testUser, -100D));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
    }

    @Test
    void whenApplyOperationToCurrentUserBalanceCalledInCaseThereIsAnAmountInBalance_ThenFindUserByCurrentUserIdAndCheckHisBalanceAndUpdateBalanceOfUser() {
        // test user
        User testUser = new User();
        testUser.setBalance(100D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(0D);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        balanceOperationService.applyOperationToCurrentUserBalance(-100D);


        assertEquals(testUser.getBalance(), expectedUser.getBalance());

        verify(mockedUserRepository, times(1)).update(testUser);
    }

    @Test
    void whenApplyOperationToCurrentUserBalanceCalledInCaseThereIsNotAnAmountInBalance_ThenFindUserByCurrentUserIdAndCheckHisBalanceAndThrown() {
        // test user
        User testUser = new User();
        testUser.setBalance(50D);

        // expected user
        User expectedUser = new User();
        expectedUser.setBalance(50D);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUser);


        assertThrows(OnSpecificUserNotEnoughMoneyException.class,
                () -> balanceOperationService.applyOperationToCurrentUserBalance(-100D));


        assertEquals(expectedUser.getBalance(), testUser.getBalance());

        verify(mockedUserRepository, never()).update(testUser);
    }

}
