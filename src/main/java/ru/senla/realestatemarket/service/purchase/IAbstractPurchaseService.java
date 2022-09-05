package ru.senla.realestatemarket.service.purchase;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IAbstractPurchaseService<M extends IModel<Long>> extends IAbstractService<M, Long> {
}
