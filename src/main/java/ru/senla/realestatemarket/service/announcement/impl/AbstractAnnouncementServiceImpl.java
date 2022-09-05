package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.announcement.IAbstractAnnouncementService;

@Slf4j
@Service
public abstract class AbstractAnnouncementServiceImpl<M extends IModel<Long>>
        extends AbstractServiceImpl<M, Long>
        implements IAbstractAnnouncementService<M> {

    @Override
    public void setDeletedStatus(Announcement announcement) {
        announcement.setStatus(AnnouncementStatusEnum.DELETED);
    }
    
}
