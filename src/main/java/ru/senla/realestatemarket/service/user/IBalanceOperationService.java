package ru.senla.realestatemarket.service.user;

import ru.senla.realestatemarket.dto.user.BalanceOperationWithoutUserIdDto;
import ru.senla.realestatemarket.dto.user.RequestBalanceOperationDto;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IBalanceOperationService extends IAbstractService<BalanceOperation, Long> {

    List<BalanceOperationWithoutUserIdDto> getAllDtoByUserId(Long userId, String rsqlQuery, String sortQuery);
    List<BalanceOperationWithoutUserIdDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery);

    BalanceOperation addByUserIdAndApplyOperation(BalanceOperation balanceOperation, Long userId);
    BalanceOperationWithoutUserIdDto addByUserIdAndApplyOperation(
            RequestBalanceOperationDto requestBalanceOperationDto, Long userId);
    BalanceOperation addFromCurrentUserAndApplyOperation(BalanceOperation balanceOperation);
    BalanceOperationWithoutUserIdDto addFromCurrentUserAndApplyOperation(
            RequestBalanceOperationDto requestBalanceOperationDto);

    void applyOperationToUserBalance(User user, Double sum);
    void applyOperationToCurrentUserBalance(Double sum);
}
