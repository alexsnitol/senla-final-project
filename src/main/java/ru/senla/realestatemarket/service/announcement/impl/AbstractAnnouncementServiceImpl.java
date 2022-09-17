package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.exception.PropertySpecificOwnerIsDifferentFromRequestedOwnerException;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.announcement.IAbstractAnnouncementService;
import ru.senla.realestatemarket.util.UserUtil;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public abstract class AbstractAnnouncementServiceImpl<M extends Announcement>
        extends AbstractServiceImpl<M, Long>
        implements IAbstractAnnouncementService<M> {

    protected final UserUtil userUtil;

    
    protected AbstractAnnouncementServiceImpl(UserUtil userUtil) {
        this.userUtil = userUtil;
    }
    

    protected void validateAccessCurrentUserToProperty(Property property) {
        Long userIdOfOwner = property.getOwner().getId();

        if (!Objects.equals(userIdOfOwner, userUtil.getCurrentUserId())) {
            String message = String.format(
                    "Access denied, because property by id %s owns another user.", userIdOfOwner);

            log.error(message);
            throw new PropertySpecificOwnerIsDifferentFromRequestedOwnerException(message);
        }
    }

    protected void setStatusIfNotNull(
            Announcement announcement, AnnouncementStatusEnum newAnnouncementStatus
    ) {
        if (newAnnouncementStatus != null) {
            AnnouncementStatusEnum oldAnnouncementStatus = announcement.getStatus();

            if (oldAnnouncementStatus != AnnouncementStatusEnum.CLOSED
                    && newAnnouncementStatus == AnnouncementStatusEnum.CLOSED) {
                announcement.setClosedDt(LocalDateTime.now());
            } else if (oldAnnouncementStatus == AnnouncementStatusEnum.CLOSED
                    && newAnnouncementStatus != AnnouncementStatusEnum.CLOSED) {
                announcement.setClosedDt(null);
            }
        }
    }
    
}
