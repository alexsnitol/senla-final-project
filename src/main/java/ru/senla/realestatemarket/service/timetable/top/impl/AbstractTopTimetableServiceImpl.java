package ru.senla.realestatemarket.service.timetable.top.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.IAbstractTopTimetableService;

public abstract class AbstractTopTimetableServiceImpl<M extends IModel<Long>>
        extends AbstractTimetableServiceImpl<M>
        implements IAbstractTopTimetableService<M> {



}
