package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.user.BalanceOperationWithoutUserIdDto;
import ru.senla.realestatemarket.dto.user.RequestBalanceOperationDto;
import ru.senla.realestatemarket.model.user.BalanceOperation;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class BalanceOperationMapper {

    public abstract BalanceOperation toBalanceOperation(RequestBalanceOperationDto requestBalanceOperationDto);

    public abstract BalanceOperationWithoutUserIdDto toBalanceOperationWithoutUserIdDto(
            BalanceOperation balanceOperation
    );

    public abstract List<BalanceOperationWithoutUserIdDto> toBalanceOperationWithoutUserIdDto(
            Collection<BalanceOperation> balanceOperations
    );

}
