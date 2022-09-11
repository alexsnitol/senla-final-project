package ru.senla.realestatemarket.service.timetable.rent.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.rent.IAbstractRentTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;

public abstract class AbstractRentTimetableServiceImpl<M extends IModel<Long>> extends AbstractTimetableServiceImpl<M>
        implements IAbstractRentTimetableService<M> {
    protected AbstractRentTimetableServiceImpl(
            IUserRepository userRepository,
            IBalanceOperationService balanceOperationService
    ) {
        super(userRepository, balanceOperationService);
    }

}
