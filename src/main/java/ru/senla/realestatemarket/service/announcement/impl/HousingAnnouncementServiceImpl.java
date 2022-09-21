package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.HousingAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IHousingAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.service.announcement.IHousingAnnouncementService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class HousingAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<HousingAnnouncement>
        implements IHousingAnnouncementService {

    private final IHousingAnnouncementRepository housingAnnouncementRepository;

    private final HousingAnnouncementMapper housingAnnouncementMapper;


    public HousingAnnouncementServiceImpl(
            IHousingAnnouncementRepository housingAnnouncementRepository,
            UserUtil userUtil,
            HousingAnnouncementMapper housingAnnouncementMapper) {
        super(userUtil);
        this.housingAnnouncementRepository = housingAnnouncementRepository;
        this.housingAnnouncementMapper = housingAnnouncementMapper;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(housingAnnouncementRepository);
        setClazz(HousingAnnouncement.class);
    }


    @Override
    @Transactional
    public List<HousingAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        return housingAnnouncementMapper.toHousingAnnouncementDtoWithMappedInheritors(getAll(rsqlQuery, sortQuery));
    }

    @Override
    @Transactional
    public List<HousingAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery) {
        List<HousingAnnouncement> housingAnnouncementList = getAll(
                GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                rsqlQuery,
                sortQuery);

        return housingAnnouncementMapper.toHousingAnnouncementDto(housingAnnouncementList);
    }

}
