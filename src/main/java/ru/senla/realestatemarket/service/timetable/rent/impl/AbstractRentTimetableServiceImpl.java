package ru.senla.realestatemarket.service.timetable.rent.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.rent.IAbstractRentTimetableService;

public abstract class AbstractRentTimetableServiceImpl<M extends IModel<Long>> extends AbstractTimetableServiceImpl<M>
        implements IAbstractRentTimetableService<M> {
}
