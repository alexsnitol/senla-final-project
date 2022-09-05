package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IAbstractAnnouncementService<M> extends IAbstractService<M, Long> {

    void setDeletedStatus(Announcement announcement);
    void setDeletedStatusByIdAndUpdate(Long id);

}
