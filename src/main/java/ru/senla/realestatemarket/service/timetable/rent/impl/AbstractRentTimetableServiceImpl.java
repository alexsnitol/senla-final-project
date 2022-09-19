package ru.senla.realestatemarket.service.timetable.rent.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.exception.BusinessRuntimeException;
import ru.senla.realestatemarket.exception.SpecificIntervalIsNotDailyException;
import ru.senla.realestatemarket.exception.SpecificIntervalIsNotMonthlyException;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.rent.IAbstractRentTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.LocalDateTime;

@Slf4j
public abstract class AbstractRentTimetableServiceImpl<M extends IModel<Long>> extends AbstractTimetableServiceImpl<M>
        implements IAbstractRentTimetableService<M> {
    protected AbstractRentTimetableServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil,
            IBalanceOperationService balanceOperationService
    ) {
        super(userRepository, userUtil, balanceOperationService);
    }


    /**
     *
     * @exception SpecificIntervalIsNotDailyException if hours, minutes and seconds
     * of specificFromDt and specificToDt not equals
     *
     * @exception SpecificIntervalIsNotMonthlyException if hours, minutes and seconds
     * of specificFromDt and specificToDt not equal and it days not equal 1
     *
     * @exception BusinessRuntimeException if announcement type is not rent
     *
     */
    protected static void validateIntervalToAnnouncementType(
            HousingAnnouncement announcement, LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        HousingAnnouncementTypeEnum announcementType = announcement.getType();
        if (announcementType.equals(HousingAnnouncementTypeEnum.DAILY_RENT)) {
            if (!(specificFromDt.getHour() == specificToDt.getHour()
                    && specificFromDt.getMinute() == specificToDt.getMinute()
                    && specificFromDt.getSecond() == specificToDt.getSecond())
            ) {
                String message = "Specific interval is not daily";

                log.error(message);
                throw new SpecificIntervalIsNotDailyException(message);
            }
        } else if (announcementType.equals(HousingAnnouncementTypeEnum.MONTHLY_RENT)) {
            if (!(specificFromDt.getDayOfMonth() == 1
                    && specificToDt.getDayOfMonth() == 1
                    && specificFromDt.getHour() == specificToDt.getHour()
                    && specificFromDt.getMinute() == specificToDt.getMinute()
                    && specificFromDt.getSecond() == specificToDt.getSecond())
            ) {
                String message = "Specific interval is not monthly";

                log.error(message);
                throw new SpecificIntervalIsNotMonthlyException(message);
            }
        } else {
            String message = "Announcement type of specific announcement is not rent";

            log.error(message);
            throw new BusinessRuntimeException(message);
        }
    }

    /**
     *
     * @exception BusinessRuntimeException if an announcement type is not rent
     *
     */
    protected static double getFinalSumByAnnouncementAndInterval(
            HousingAnnouncement announcement, LocalDateTime specificFromDt, LocalDateTime specificToDt
    ) {
        double finalSum;
        double price = announcement.getPrice();

        HousingAnnouncementTypeEnum announcementType = announcement.getType();
        if (announcementType.equals(HousingAnnouncementTypeEnum.DAILY_RENT)) {
            finalSum = calculateSumByDateTimes(price / 24, specificFromDt, specificToDt);
        } else if (announcementType.equals(HousingAnnouncementTypeEnum.MONTHLY_RENT)) {
            finalSum = calculateSumByDateTimes((price / 30) / 24, specificFromDt, specificToDt);
        } else {
            String message = String.format(
                    "Announcement type of announcement by id %s is not rent", announcement.getId());

            log.error(message);
            throw new BusinessRuntimeException(message);
        }
        return finalSum;
    }

}
