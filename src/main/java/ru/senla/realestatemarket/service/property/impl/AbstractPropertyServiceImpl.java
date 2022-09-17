package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.exception.PropertySpecificOwnerIsDifferentFromRequestedOwnerException;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.property.IPropertyWithAnnouncementList;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.IAbstractPropertyService;
import ru.senla.realestatemarket.util.UserUtil;

import java.util.List;

@Slf4j
public abstract class AbstractPropertyServiceImpl
        <A extends Announcement, P extends Property & IPropertyWithAnnouncementList<A>>
        extends AbstractServiceImpl<P, Long>
        implements IAbstractPropertyService<P> {

    protected final IUserRepository userRepository;
    protected final UserUtil userUtil;


    protected AbstractPropertyServiceImpl(
            IUserRepository userRepository,
            UserUtil userUtil
    ) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }


    protected void validateAccessCurrentUserToProperty(P property) {
        if (!property.getOwner().getId().equals(userUtil.getCurrentUserId())) {
            String message = "Access denied, because owner of it property is different from requested owner";

            log.error(message);
            throw new PropertySpecificOwnerIsDifferentFromRequestedOwnerException(message);
        }
    }

    protected void setOwnerByUserIdOfOwner(P property, Long userOfOwnerId) {
        User owner = userRepository.findById(userOfOwnerId);
        EntityHelper.checkEntityOnNull(owner, User.class, userOfOwnerId);

        property.setOwner(owner);
    }

    protected void setDeletedStatusOnPropertyAndRelatedAnnouncements(P property) {
        property.setStatus(PropertyStatusEnum.DELETED);

        List<A> apartmentAnnouncementList = property.getAnnouncementList();
        for(A a : apartmentAnnouncementList) {
            a.setStatus(AnnouncementStatusEnum.DELETED);
        }
    }

}
