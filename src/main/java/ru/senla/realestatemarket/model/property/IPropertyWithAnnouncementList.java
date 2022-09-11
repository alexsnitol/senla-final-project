package ru.senla.realestatemarket.model.property;

import ru.senla.realestatemarket.model.announcement.Announcement;

import java.util.List;

public interface IPropertyWithAnnouncementList<M extends Announcement> {

    List<M> getAnnouncementList();
    void setAnnouncementList(List<M> announcementList);

}
