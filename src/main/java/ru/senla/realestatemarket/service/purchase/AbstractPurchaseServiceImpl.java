package ru.senla.realestatemarket.service.purchase;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.AbstractServiceImpl;

public abstract class AbstractPurchaseServiceImpl<M extends IModel<Long>> extends AbstractServiceImpl<M, Long>
        implements IAbstractPurchaseService<M> {

}
