package ru.senla.realestatemarket.service.timetable.top.impl;

import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.timetable.AbstractTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.IAbstractTopTimetableService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;

public abstract class AbstractTopTimetableServiceImpl<M extends IModel<Long>>
        extends AbstractTimetableServiceImpl<M>
        implements IAbstractTopTimetableService<M> {

    protected final IAnnouncementTopPriceRepository announcementTopPriceRepository;

    protected AbstractTopTimetableServiceImpl(IUserRepository userRepository,
                                              IBalanceOperationService balanceOperationService,
                                              IAnnouncementTopPriceRepository announcementTopPriceRepository) {
        super(userRepository, balanceOperationService);
        this.announcementTopPriceRepository = announcementTopPriceRepository;
    }

}
