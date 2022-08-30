package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.announcement.IAbstractAnnouncementService;

@Slf4j
@Service
public abstract class AbstractAnnouncementServiceImpl<M, D>
        extends AbstractServiceImpl<M, Long>
        implements IAbstractAnnouncementService<M, D> {
}
