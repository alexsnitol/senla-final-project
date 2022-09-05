package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.LandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestLandAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestLandAnnouncementDto;
import ru.senla.realestatemarket.exception.InvalidStatusAnnouncementException;
import ru.senla.realestatemarket.mapper.announcement.LandAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.announcement.SellAnnouncementStatusEnum;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.service.announcement.ILandAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class LandAnnouncementServiceImpl
        extends AbstractAnnouncementServiceImpl<LandAnnouncement>
        implements ILandAnnouncementService {

    private final ILandAnnouncementRepository landAnnouncementRepository;
    private final ILandPropertyRepository landPropertyRepository;

    private final LandAnnouncementMapper landAnnouncementMapper
            = Mappers.getMapper(LandAnnouncementMapper.class);


    public LandAnnouncementServiceImpl(ILandAnnouncementRepository landAnnouncementRepository,
                                       ILandPropertyRepository landPropertyRepository) {
        this.landAnnouncementRepository = landAnnouncementRepository;
        this.landPropertyRepository = landPropertyRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(landAnnouncementRepository);
        setClazz(LandAnnouncement.class);
    }


    @Override
    @Transactional
    public List<LandAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<LandAnnouncement> landAnnouncementList;

        if (sortQuery == null) {
            // default sort
            landAnnouncementList = getAll(rsqlQuery,
                    AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            landAnnouncementList = getAll(rsqlQuery, sortQuery);
        }

        return landAnnouncementMapper.toLandAnnouncementDto(landAnnouncementList);
    }

    @Override
    public LandAnnouncementDto getDtoById(Long id) {
        return landAnnouncementMapper.toLandAnnouncementDto(getById(id));
    }

    @Override
    @Transactional
    public void add(RequestLandAnnouncementDto requestLandAnnouncementDto) {
        LandAnnouncement landAnnouncement
                = landAnnouncementMapper.toLandAnnouncement(requestLandAnnouncementDto);

        Long landPropertyId = requestLandAnnouncementDto.getLandPropertyId();
        setLandPropertyById(landAnnouncement, landPropertyId);


        landAnnouncementRepository.create(landAnnouncement);
    }

    private void setLandPropertyById(LandAnnouncement landAnnouncement, Long landPropertyId) {
        LandProperty landProperty = landPropertyRepository.findById(landPropertyId);
        EntityHelper.checkEntityOnNull(landProperty, LandProperty.class, landPropertyId);

        landAnnouncement.setProperty(landProperty);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestLandAnnouncementDto updateRequestLandAnnouncementDto, Long id) {
        LandAnnouncement landAnnouncement = getById(id);


        Long landPropertyId = updateRequestLandAnnouncementDto.getLandPropertyId();
        if (landPropertyId != null) {
            setLandPropertyById(landAnnouncement, landPropertyId);
        }


        AnnouncementStatusEnum announcementStatus = updateRequestLandAnnouncementDto.getStatus();
        if (announcementStatus != null) {
            validSellAnnouncementTypeOnAccordanceWithStatus(announcementStatus);
        }


        landAnnouncementMapper.updateLandAnnouncementFromUpdateRequestLandAnnouncement(
                updateRequestLandAnnouncementDto, landAnnouncement
        );


        landAnnouncementRepository.update(landAnnouncement);
    }

    private void validSellAnnouncementTypeOnAccordanceWithStatus(AnnouncementStatusEnum announcementStatus) {
        try {
            SellAnnouncementStatusEnum.valueOf(announcementStatus.name());
        } catch (IllegalArgumentException e) {
            String message = "Announcement with sell type can't have rent status";

            log.error(message);
            throw new InvalidStatusAnnouncementException(message);
        }
    }

    @Override
    @Transactional
    public void setDeletedStatusByIdAndUpdate(Long id) {
        LandAnnouncement landAnnouncement = getById(id);
        
        setDeletedStatus(landAnnouncement);
        
        landAnnouncementRepository.update(landAnnouncement);
    }
    
}
