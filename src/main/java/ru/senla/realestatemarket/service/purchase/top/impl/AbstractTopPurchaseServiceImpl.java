package ru.senla.realestatemarket.service.purchase.top.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.purchase.AbstractPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.top.IAbstractTopPurchaseService;

public abstract class AbstractTopPurchaseServiceImpl<M extends IModel<Long>>
        extends AbstractPurchaseServiceImpl<M>
        implements IAbstractTopPurchaseService<M> {



}
