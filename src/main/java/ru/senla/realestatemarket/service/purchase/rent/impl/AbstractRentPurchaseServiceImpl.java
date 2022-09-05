package ru.senla.realestatemarket.service.purchase.rent.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.purchase.AbstractPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.rent.IAbstractRentPurchaseService;

public abstract class AbstractRentPurchaseServiceImpl<M extends IModel<Long>> extends AbstractPurchaseServiceImpl<M>
        implements IAbstractRentPurchaseService<M> {
}
