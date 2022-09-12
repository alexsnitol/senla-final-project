package ru.senla.realestatemarket.service.timetable.top.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.IAbstractTopTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;

import java.time.LocalDateTime;

@Slf4j
public abstract class AbstractTopTimetableServiceImpl<M extends IModel<Long>>
        extends AbstractTimetableServiceImpl<M>
        implements IAbstractTopTimetableService<M> {

    protected final IAnnouncementTopPriceRepository announcementTopPriceRepository;

    protected AbstractTopTimetableServiceImpl(IUserRepository userRepository,
                                              IBalanceOperationService balanceOperationService,
                                              IAnnouncementTopPriceRepository announcementTopPriceRepository) {
        super(userRepository, balanceOperationService);
        this.announcementTopPriceRepository = announcementTopPriceRepository;
    }

    /**
     * @exception IllegalArgumentException if one of params have not zeros in minutes, seconds and nanoseconds
     */
    protected static void checkForSpecificFromAndToHaveZerosMinutesAndSecondsAndNanoSeconds(
            LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        if (!(specificFromDt.getMinute() == 0
                && specificFromDt.getSecond() == 0
                && specificFromDt.getNano() == 0)
                ||
                !(specificToDt.getMinute() == 0
                        && specificToDt.getSecond() == 0
                        && specificToDt.getNano() == 0)
        ) {
            String message = "Specific date times must have been zeros minutes, seconds and nanoseconds";

            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

}
