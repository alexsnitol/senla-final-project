package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.BalanceOperationWithoutUserIdDto;
import ru.senla.realestatemarket.dto.user.RequestBalanceOperationDto;
import ru.senla.realestatemarket.exception.OnSpecificUserNotEnoughMoneyException;
import ru.senla.realestatemarket.mapper.user.BalanceOperationMapper;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.model.user.BalanceOperationCommentEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IBalanceOperationRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.user.specification.BalanceOperationSpecification.hasUserId;

@Slf4j
@Service
public class BalanceOperationServiceImpl
        extends AbstractServiceImpl<BalanceOperation, Long>
        implements IBalanceOperationService {

    private final IBalanceOperationRepository balanceOperationRepository;
    private final IUserRepository userRepository;

    private final BalanceOperationMapper balanceOperationMapper = Mappers.getMapper(BalanceOperationMapper.class);


    public BalanceOperationServiceImpl(IBalanceOperationRepository balanceOperationRepository,
                                       IUserRepository userRepository) {
        this.balanceOperationRepository = balanceOperationRepository;
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(balanceOperationRepository);
        setClazz(BalanceOperation.class);
    }

    @Override
    public List<BalanceOperationWithoutUserIdDto> getAllDtoByUserId(Long userId, String rsqlQuery, String sortQuery) {
        List<BalanceOperation> balanceOperations;

        if (sortQuery == null) {
             balanceOperations = getAll(hasUserId(userId), rsqlQuery,
                     Sort.by(Sort.Direction.DESC, "createdDt"));
        } else {
            balanceOperations = getAll(hasUserId(userId), rsqlQuery, sortQuery);
        }

        return balanceOperationMapper.toBalanceOperationWithoutUserIdDto(balanceOperations);
    }

    @Override
    public List<BalanceOperationWithoutUserIdDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        return getAllDtoByUserId(UserUtil.getCurrentUserId(), rsqlQuery, sortQuery);
    }

    @Override
    @Transactional
    public void addByUserIdAndApplyOperation(BalanceOperation balanceOperation, Long userId) {
        User user = userRepository.findById(userId);
        EntityHelper.checkEntityOnNull(user, User.class, userId);


        Double sum = balanceOperation.getSum();
        applyOperationToUserBalance(user, sum);

        balanceOperation.setUser(user);


        if (balanceOperation.getComment() == null && sum > 0) {
            balanceOperation.setComment(BalanceOperationCommentEnum.TRANSFER.name());
        }


        balanceOperationRepository.create(balanceOperation);
    }

    @Override
    @Transactional
    public void addByUserIdAndApplyOperation(RequestBalanceOperationDto requestBalanceOperationDto, Long userId) {
        BalanceOperation balanceOperation = balanceOperationMapper.toBalanceOperation(requestBalanceOperationDto);

        addByUserIdAndApplyOperation(balanceOperation, userId);
    }

    @Override
    @Transactional
    public void addFromCurrentUserAndApplyOperation(BalanceOperation balanceOperation) {
        addByUserIdAndApplyOperation(balanceOperation, UserUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public void addFromCurrentUserAndApplyOperation(RequestBalanceOperationDto requestBalanceOperationDto) {
        addByUserIdAndApplyOperation(requestBalanceOperationDto, UserUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public void applyOperationToUserBalance(User user, Double sum) {
        Double currentBalance = user.getBalance();
        user.setBalance(currentBalance + sum);

        if (user.getBalance() < 0) {
            String message = String.format(
                    "User with id %s not enough money for this operation. Not enough %s.",
                    user.getId(), user.getBalance() * -1);

            log.error(message);
            throw new OnSpecificUserNotEnoughMoneyException(message);
        }

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void applyOperationToCurrentUserBalance(Double sum) {
        User user = userRepository.findById(UserUtil.getCurrentUserId());

        applyOperationToUserBalance(user, sum);
    }

}
