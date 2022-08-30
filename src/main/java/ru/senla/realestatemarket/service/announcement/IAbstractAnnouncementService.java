package ru.senla.realestatemarket.service.announcement;

import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IAbstractAnnouncementService<M, D> extends IAbstractService<M, Long> {

    List<D> getAllDto(String rsqlQuery, String sortQuery);

}
