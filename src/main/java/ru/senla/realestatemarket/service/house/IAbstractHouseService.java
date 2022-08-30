package ru.senla.realestatemarket.service.house;

import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IAbstractHouseService<M, D> extends IAbstractService<M, Long> {

    List<D> getAllDto(String rsqlQuery, String sortQuery);

}
