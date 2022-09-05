package ru.senla.realestatemarket.service.timetable;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.AbstractServiceImpl;

public abstract class AbstractTimetableServiceImpl<M extends IModel<Long>> extends AbstractServiceImpl<M, Long>
        implements IAbstractTimetableService<M> {
}
