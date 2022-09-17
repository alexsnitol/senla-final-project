package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.exception.InvalidStatusAnnouncementException;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.RentAnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.SellAnnouncementStatusEnum;
import ru.senla.realestatemarket.service.announcement.IAbstractHousingAnnouncementService;
import ru.senla.realestatemarket.util.UserUtil;

@Slf4j
@Service
public abstract class AbstractHousingAnnouncementServiceImpl<M extends HousingAnnouncement>
        extends AbstractAnnouncementServiceImpl<M>
        implements IAbstractHousingAnnouncementService<M> {

    protected AbstractHousingAnnouncementServiceImpl(UserUtil userUtil) {
        super(userUtil);
    }

    
    protected void validateAnnouncementTypeOnAccordanceWithStatus(
            HousingAnnouncementTypeEnum housingAnnouncementType,
            AnnouncementStatusEnum announcementStatus) throws InvalidStatusAnnouncementException
    {
        if (housingAnnouncementType.equals(HousingAnnouncementTypeEnum.SELL)) {
            try {
                SellAnnouncementStatusEnum.valueOf(announcementStatus.name());
            } catch (IllegalArgumentException e) {
                String message = "Announcement with sell type can't have rent status";

                log.error(message);
                throw new InvalidStatusAnnouncementException(message);
            }
        } else {
            try {
                RentAnnouncementStatusEnum.valueOf(announcementStatus.name());
            } catch (IllegalArgumentException e) {
                String message = "Announcement with rent type can't have sell status";

                log.error(message);
                throw new InvalidStatusAnnouncementException(message);
            }
        }
    }

    protected void validateAnnouncementTypeOnAccordanceWithStatusIfItNotNull(
            HousingAnnouncement housingAnnouncement,
            @Nullable HousingAnnouncementTypeEnum announcementType, @Nullable AnnouncementStatusEnum announcementStatus
    ) {
        if (announcementType != null || announcementStatus != null) {
            if (announcementType == null) {
                announcementType = housingAnnouncement.getType();
            }

            if (announcementStatus == null) {
                announcementStatus = housingAnnouncement.getStatus();
            }

            validateAnnouncementTypeOnAccordanceWithStatus(announcementType, announcementStatus);
        }
    }

}
