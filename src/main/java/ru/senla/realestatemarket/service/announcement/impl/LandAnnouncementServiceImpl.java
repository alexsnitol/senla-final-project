package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
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
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.service.announcement.ILandAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty;
import static ru.senla.realestatemarket.repo.announcement.specification.LandAnnouncementSpecification.hasUserIdOfOwnerInProperty;

@Slf4j
@Service
public class LandAnnouncementServiceImpl
        extends AbstractAnnouncementServiceImpl<LandAnnouncement>
        implements ILandAnnouncementService {

    private final ILandAnnouncementRepository landAnnouncementRepository;
    private final ILandPropertyRepository landPropertyRepository;

    private final LandAnnouncementMapper landAnnouncementMapper;


    public LandAnnouncementServiceImpl(ILandAnnouncementRepository landAnnouncementRepository,
                                       ILandPropertyRepository landPropertyRepository,
                                       UserUtil userUtil,
                                       LandAnnouncementMapper landAnnouncementMapper) {
        super(userUtil);
        this.landAnnouncementRepository = landAnnouncementRepository;
        this.landPropertyRepository = landPropertyRepository;
        this.landAnnouncementMapper = landAnnouncementMapper;
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
    @Transactional
    public List<LandAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery) {
        List<LandAnnouncement> landAnnouncementList;

        if (sortQuery == null) {
            // default sort
            landAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            landAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    sortQuery);
        }

        return landAnnouncementMapper.toLandAnnouncementDto(landAnnouncementList);
    }

    @Override
    @Transactional
    public List<LandAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(Long useIdOfOwner, String rsqlQuery, String sortQuery) {
        List<LandAnnouncement> landAnnouncementList;

        if (sortQuery == null) {
            // default sort
            landAnnouncementList = getAll(
                    LandAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            LandAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            landAnnouncementList = getAll(
                    LandAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            LandAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    sortQuery);
        }

        return landAnnouncementMapper.toLandAnnouncementDto(landAnnouncementList);
    }

    @Override
    @Transactional
    public List<LandAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        List<LandAnnouncement> landAnnouncementList;

        if (sortQuery == null) {
            // default sort
            landAnnouncementList = getAll(hasUserIdOfOwnerInProperty(userUtil.getCurrentUserId()),
                    rsqlQuery, AnnouncementSort.byCreatedDtAsc());
        } else {
            landAnnouncementList = getAll(hasUserIdOfOwnerInProperty(userUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return landAnnouncementMapper.toLandAnnouncementDto(landAnnouncementList);
    }

    @Override
    @Transactional
    public List<LandAnnouncementDto> getAllByKeyWords(String keyWords) {
        List<String> keyWordsSplit = Arrays.stream(keyWords.split(",")).collect(Collectors.toList());

        return landAnnouncementMapper.toLandAnnouncementDto(
                landAnnouncementRepository.findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit));
    }

    @Override
    @Transactional
    public LandAnnouncementDto getDtoById(Long id) {
        return landAnnouncementMapper.toLandAnnouncementDto(getById(id));
    }

    @Override
    @Transactional
    public LandAnnouncementDto getByIdWithOpenStatusDto(Long id) {
        return landAnnouncementMapper.toLandAnnouncementDto(
                getOne(LandAnnouncementSpecification.hasId(id).and(
                        LandAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN))))
        );
    }

    @Override
    @Transactional
    public LandAnnouncementDto getByIdDtoOfCurrentUser(Long id) {
        return landAnnouncementMapper.toLandAnnouncementDto(
                getOne(hasIdAndUserIdOfOwnerInProperty(id, userUtil.getCurrentUserId()))
        );
    }

    @Override
    @Transactional
    public void addFromDto(RequestLandAnnouncementDto requestDto) {
        LandAnnouncement landAnnouncement
                = landAnnouncementMapper.toLandAnnouncement(requestDto);

        Long landPropertyId = requestDto.getLandPropertyId();
        setLandPropertyById(landAnnouncement, landPropertyId);


        landAnnouncementRepository.create(landAnnouncement);
    }

    @Override
    @Transactional
    public void addFromDtoFromCurrentUser(RequestLandAnnouncementDto requestDto) {
        Long landPropertyId = requestDto.getLandPropertyId();

        LandProperty landProperty = landPropertyRepository.findById(landPropertyId);
        EntityHelper.checkEntityOnNull(landProperty, LandProperty.class, landPropertyId);

        validateAccessCurrentUserToProperty(landProperty);

        addFromDto(requestDto);
    }

    @Override
    @Transactional
    public void updateFromDtoById(UpdateRequestLandAnnouncementDto updateRequestDto, Long id) {
        LandAnnouncement landAnnouncement = getById(id);
        
        updateFromDto(updateRequestDto, landAnnouncement);
    }

    @Override
    @Transactional
    public void updateByIdFromCurrentUser(UpdateRequestLandAnnouncementDto updateRequestDto, Long id) {
        LandAnnouncement landAnnouncement = getOne(hasIdAndUserIdOfOwnerInProperty(id, userUtil.getCurrentUserId()));


        Long landPropertyId = updateRequestDto.getLandPropertyId();
        LandProperty landProperty = landPropertyRepository.findById(landPropertyId);
        EntityHelper.checkEntityOnNull(landProperty, LandProperty.class, landPropertyId);

        validateAccessCurrentUserToProperty(landProperty);


        updateFromDto(updateRequestDto, landAnnouncement);
    }

    private void updateFromDto(UpdateRequestLandAnnouncementDto updateRequestDto, LandAnnouncement landAnnouncement) {
        Long landPropertyId = updateRequestDto.getLandPropertyId();
        if (landPropertyId != null) {
            setLandPropertyById(landAnnouncement, landPropertyId);
        }


        AnnouncementStatusEnum announcementStatus = updateRequestDto.getStatus();
        if (announcementStatus != null) {
            validSellAnnouncementTypeOnAccordanceWithStatus(announcementStatus);
        }

        setStatusIfNotNull(landAnnouncement, announcementStatus);

        landAnnouncementMapper.updateLandAnnouncementFromUpdateRequestLandAnnouncement(
                updateRequestDto, landAnnouncement
        );


        landAnnouncementRepository.update(landAnnouncement);
    }

    private void setLandPropertyById(LandAnnouncement landAnnouncement, Long landPropertyId) {
        LandProperty landProperty = landPropertyRepository.findById(landPropertyId);
        EntityHelper.checkEntityOnNull(landProperty, LandProperty.class, landPropertyId);

        landAnnouncement.setProperty(landProperty);
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

}
