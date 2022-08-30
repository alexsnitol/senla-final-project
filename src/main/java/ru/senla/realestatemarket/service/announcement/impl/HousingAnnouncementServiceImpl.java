package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.HousingAnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.HousingAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IHousingAnnouncementRepository;
import ru.senla.realestatemarket.service.announcement.IHousingAnnouncementService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class HousingAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<HousingAnnouncement, HousingAnnouncementDto>
        implements IHousingAnnouncementService {

    private final IHousingAnnouncementRepository housingAnnouncementRepository;

    private final HousingAnnouncementMapper housingAnnouncementMapper
            = Mappers.getMapper(HousingAnnouncementMapper.class);


    public HousingAnnouncementServiceImpl(IHousingAnnouncementRepository housingAnnouncementRepository) {
        this.housingAnnouncementRepository = housingAnnouncementRepository;
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
}
