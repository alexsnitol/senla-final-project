package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.service.announcement.IAbstractHousingAnnouncementService;

@Slf4j
@Service
public abstract class AbstractHousingAnnouncementServiceImpl<M, D>
        extends AbstractAnnouncementServiceImpl<M, D>
        implements IAbstractHousingAnnouncementService<M, D> {
}
