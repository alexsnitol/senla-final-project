package ru.senla.realestatemarket.repo.announcement.impl;

import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.announcement.IAbstractAnnouncementRepository;

public abstract class AbstractAnnouncementRepositoryImpl<M>
        extends AbstractRepositoryImpl<M, Long>
        implements IAbstractAnnouncementRepository<M> {
}
