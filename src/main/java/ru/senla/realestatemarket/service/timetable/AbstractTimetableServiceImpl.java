package ru.senla.realestatemarket.service.timetable;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.exception.InSpecificIntervalExistRecordsException;
import ru.senla.realestatemarket.exception.OnSpecificUserNotEnoughMoneyException;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification.concernsTheIntervalBetweenSpecificFromAndToExcludingIntervalItself;
import static ru.senla.realestatemarket.repo.timetable.specification.GenericTimetableSpecification.intervalWithSpecificFromAndTo;

@Slf4j
public abstract class AbstractTimetableServiceImpl<M extends IModel<Long>> extends AbstractServiceImpl<M, Long>
        implements IAbstractTimetableService<M> {


    protected final IUserRepository userRepository;
    protected final UserUtil userUtil;
    protected final IBalanceOperationService balanceOperationService;


    protected AbstractTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService
    ) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
        this.balanceOperationService = balanceOperationService;
    }


    protected static double calculateSumByDateTimes(double pricePerHour, LocalDateTime formDt, LocalDateTime toDt) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

        long sumOfHour = ((toDt.toEpochSecond(zoneOffset) - formDt.toEpochSecond(zoneOffset)) / 60) / 60;

        return sumOfHour * pricePerHour;
    }

    protected void checkBalanceOfCurrentUserToApplyOperationWithSpecificSum(double finalSum) {
        User currentUser = userRepository.findById(userUtil.getCurrentUserId());
        if (currentUser.getBalance() < finalSum) {
            String message = String.format(
                    "User with id %s not enough money for this operation. Not enough %s.",
                    currentUser.getId(), (finalSum - currentUser.getBalance()));

            log.error(message);
            throw new OnSpecificUserNotEnoughMoneyException(message);
        }
    }

    protected void checkForRecordsInSpecificIntervalExcludingIntervalItself(
            LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        if (defaultRepository
                .isExist(concernsTheIntervalBetweenSpecificFromAndToExcludingIntervalItself(
                        specificFromDt, specificToDt))
            || defaultRepository
                .isExist(intervalWithSpecificFromAndTo(
                        specificFromDt, specificToDt))
        ) {
            String message = "In specific interval exist records. Adding new record impossible.";

            log.error(message);
            throw new InSpecificIntervalExistRecordsException(message);
        }
    }

}
