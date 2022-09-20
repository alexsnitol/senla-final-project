package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
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
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class BalanceOperationServiceImpl
        extends AbstractServiceImpl<BalanceOperation, Long>
        implements IBalanceOperationService {

    private final IBalanceOperationRepository balanceOperationRepository;
    private final IUserRepository userRepository;
    private final UserUtil userUtil;

    private final BalanceOperationMapper balanceOperationMapper;


    public BalanceOperationServiceImpl(IBalanceOperationRepository balanceOperationRepository,
                                       IUserRepository userRepository,
                                       UserUtil userUtil,
                                       BalanceOperationMapper balanceOperationMapper) {
        this.balanceOperationRepository = balanceOperationRepository;
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.balanceOperationMapper = balanceOperationMapper;
    }


    @PostConstruct
    public void init() {
        setDefaultRepository(balanceOperationRepository);
        setClazz(BalanceOperation.class);
    }

    @Override
    @Transactional
    public List<BalanceOperationWithoutUserIdDto> getAllDtoByUserId(Long userId, String rsqlQuery, String sortQuery) {
        List<BalanceOperation> balanceOperations;

        if (sortQuery == null) {
             balanceOperations = balanceOperationRepository.findAllByUserId(
                     userId, rsqlQuery, Sort.by(Sort.Direction.DESC, "createdDt"));
        } else {
            balanceOperations = balanceOperationRepository.findAllByUserId(
                    userId, rsqlQuery, SortUtil.parseSortQuery(sortQuery));
        }

        return balanceOperationMapper.toBalanceOperationWithoutUserIdDto(balanceOperations);
    }

    @Override
    @Transactional
    public List<BalanceOperationWithoutUserIdDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        return getAllDtoByUserId(userUtil.getCurrentUserId(), rsqlQuery, sortQuery);
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
        addByUserIdAndApplyOperation(balanceOperation, userUtil.getCurrentUserId());
    }

    @Override
    @Transactional
    public void addFromCurrentUserAndApplyOperation(RequestBalanceOperationDto requestBalanceOperationDto) {
        addByUserIdAndApplyOperation(requestBalanceOperationDto, userUtil.getCurrentUserId());
    }

    /**
     * @exception  OnSpecificUserNotEnoughMoneyException if a specific user has a not specific sum on it balance
     */
    @Override
    @Transactional
    public void applyOperationToUserBalance(User user, Double sum) {
        Double currentBalance = user.getBalance();

        if (sum < 0 && user.getBalance() < -sum) {
            String message = String.format(
                    "User with id %s not enough money for this operation. Not enough %s.",
                    user.getId(), (user.getBalance() + sum) * -1);

            log.error(message);
            throw new OnSpecificUserNotEnoughMoneyException(message);
        }

        user.setBalance(currentBalance + sum);

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void applyOperationToCurrentUserBalance(Double sum) {
        User user = userRepository.findById(userUtil.getCurrentUserId());

        applyOperationToUserBalance(user, sum);
    }

}
