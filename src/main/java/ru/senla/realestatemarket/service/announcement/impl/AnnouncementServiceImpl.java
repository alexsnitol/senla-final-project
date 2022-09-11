package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.AnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.AnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.repo.announcement.IAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.service.announcement.IAnnouncementService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class AnnouncementServiceImpl
        extends AbstractAnnouncementServiceImpl<Announcement> implements IAnnouncementService {

    private final IAnnouncementRepository announcementRepository;

    private final AnnouncementMapper announcementMapper = Mappers.getMapper(AnnouncementMapper.class);


    public AnnouncementServiceImpl(IAnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(announcementRepository);
        setClazz(Announcement.class);
    }

    @Override
    @Transactional
    public List<AnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        return announcementMapper.toAnnouncementDtoWithMappedInheritors(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public List<AnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery) {
        List<Announcement> announcementList = getAll(
                GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                rsqlQuery,
                sortQuery);

        return announcementMapper.toAnnouncementDtoWithMappedInheritors(announcementList);
    }

}
