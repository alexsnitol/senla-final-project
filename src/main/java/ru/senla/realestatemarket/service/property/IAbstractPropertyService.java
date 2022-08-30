package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IAbstractPropertyService<M, D> extends IAbstractService<M, Long> {

    List<D> getAllDto(String rsqlQuery, String sortQuery);

}
