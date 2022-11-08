package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.FamilyHouseAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort;
import ru.senla.realestatemarket.repo.announcement.specification.FamilyHouseAnnouncementSpecification;
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.repo.property.IFamilyHousePropertyRepository;
import ru.senla.realestatemarket.service.announcement.IFamilyHouseAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.SearchQueryUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc;
import static ru.senla.realestatemarket.repo.announcement.specification.FamilyHouseAnnouncementSpecification.hasUserIdOfOwnerInProperty;
import static ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class FamilyHouseAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<FamilyHouseAnnouncement>
        implements IFamilyHouseAnnouncementService {

    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final IFamilyHousePropertyRepository familyHousePropertyRepository;

    private final FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper;


    public FamilyHouseAnnouncementServiceImpl(
            IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
            IFamilyHousePropertyRepository familyHousePropertyRepository,
            UserUtil userUtil,
            FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper
    ) {
        super(userUtil);

        this.clazz = FamilyHouseAnnouncement.class;
        this.defaultRepository = familyHouseAnnouncementRepository;

        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.familyHousePropertyRepository = familyHousePropertyRepository;
        this.familyHouseAnnouncementMapper = familyHouseAnnouncementMapper;
    }


    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseAnnouncement> familyHouseAnnouncementList;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementList = getAll(rsqlQuery, byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            familyHouseAnnouncementList = getAll(rsqlQuery, sortQuery);
        }

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementList);
    }

    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseAnnouncement> familyHouseAnnouncementList;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            familyHouseAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    sortQuery);
        }

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementList);
    }

    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(
            Long useIdOfOwner, String rsqlQuery, String sortQuery
    ) {
        List<FamilyHouseAnnouncement> familyHouseAnnouncementList;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementList = getAll(
                    FamilyHouseAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            FamilyHouseAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            familyHouseAnnouncementList = getAll(
                    FamilyHouseAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            FamilyHouseAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    sortQuery);
        }

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementList);
    }

    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        List<FamilyHouseAnnouncement> familyHouseAnnouncementList;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementList = getAll(hasUserIdOfOwnerInProperty(userUtil.getCurrentUserId()),
                    rsqlQuery, AnnouncementSort.byCreatedDtAsc());
        } else {
            familyHouseAnnouncementList = getAll(hasUserIdOfOwnerInProperty(userUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementList);
    }

    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllByKeyWords(String keyWords) {
        List<String> keyWordsSplit = SearchQueryUtil.getKeyWordsSpit(keyWords);

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(
                familyHouseAnnouncementRepository.findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit));
    }

    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllWithOpenStatusByKeyWords(String keyWords) {
        List<String> keyWordsSplit = SearchQueryUtil.getKeyWordsSpit(keyWords);

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(
                familyHouseAnnouncementRepository.findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(
                        keyWordsSplit));
    }

    @Override
    @Transactional
    public FamilyHouseAnnouncementDto getDtoById(Long id) {
        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(getById(id));
    }

    @Override
    @Transactional
    public FamilyHouseAnnouncementDto getByIdWithOpenStatusDto(Long id) {
        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(
                getOne(FamilyHouseAnnouncementSpecification.hasId(id).and(
                        FamilyHouseAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN))))
        );
    }

    @Override
    @Transactional
    public FamilyHouseAnnouncementDto getByIdDtoOfCurrentUser(Long id) {
        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(
                getOne(hasIdAndUserIdOfOwnerInProperty(id, userUtil.getCurrentUserId()))
        );
    }

    @Override
    @Transactional
    public FamilyHouseAnnouncementDto addFromDto(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementMapper.toFamilyHouseAnnouncement(requestFamilyHouseAnnouncementDto);

        Long familyHousePropertyId = requestFamilyHouseAnnouncementDto.getFamilyHousePropertyId();
        setFamilyHouseAnnouncementById(familyHouseAnnouncement, familyHousePropertyId);


        FamilyHouseAnnouncement familyHouseAnnouncementResponse
                = familyHouseAnnouncementRepository.create(familyHouseAnnouncement);

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementResponse);
    }

    @Override
    @Transactional
    public FamilyHouseAnnouncementDto addFromCurrentUser(
            RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto
    ) {
        Long familyHousePropertyId = requestFamilyHouseAnnouncementDto.getFamilyHousePropertyId();

        FamilyHouseProperty familyHouseProperty = familyHousePropertyRepository.findById(familyHousePropertyId);
        EntityHelper.checkEntityOnNull(familyHouseProperty, FamilyHouseProperty.class, familyHousePropertyId);

        validateAccessCurrentUserToProperty(familyHouseProperty);

        return addFromDto(requestFamilyHouseAnnouncementDto);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto, Long id) {
        FamilyHouseAnnouncement familyHouseAnnouncement = getById(id);

        updateFromDto(updateRequestFamilyHouseAnnouncementDto, familyHouseAnnouncement);
    }

    @Override
    @Transactional
    public void updateByIdFromCurrentUser(
            UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto, Long id
    ) {
        FamilyHouseAnnouncement familyHouseAnnouncement = getOne(
                hasIdAndUserIdOfOwnerInProperty(id, userUtil.getCurrentUserId()));


        Long familyHousePropertyId = updateRequestFamilyHouseAnnouncementDto.getFamilyHousePropertyId();
        if (familyHousePropertyId != null) {
            FamilyHouseProperty familyHouseProperty = familyHousePropertyRepository.findById(familyHousePropertyId);
            EntityHelper.checkEntityOnNull(familyHouseProperty, FamilyHouseProperty.class, familyHousePropertyId);

            validateAccessCurrentUserToProperty(familyHouseProperty);
        }


        updateFromDto(updateRequestFamilyHouseAnnouncementDto, familyHouseAnnouncement);
    }

    private void updateFromDto(UpdateRequestFamilyHouseAnnouncementDto updateRequestFamilyHouseAnnouncementDto,
                               FamilyHouseAnnouncement familyHouseAnnouncement) {
        Long familyHousePropertyId = updateRequestFamilyHouseAnnouncementDto.getFamilyHousePropertyId();
        if (familyHousePropertyId != null) {
            setFamilyHouseAnnouncementById(familyHouseAnnouncement, familyHousePropertyId);
        }


        HousingAnnouncementTypeEnum announcementType = updateRequestFamilyHouseAnnouncementDto.getType();
        AnnouncementStatusEnum announcementStatus = updateRequestFamilyHouseAnnouncementDto.getStatus();

        validateAnnouncementTypeOnAccordanceWithStatusIfItNotNull(
                familyHouseAnnouncement, announcementType, announcementStatus);

        setStatusIfNotNull(familyHouseAnnouncement, announcementStatus);

        familyHouseAnnouncementMapper.updateFamilyHouseAnnouncementFormUpdateRequestFamilyHouseAnnouncement(
                updateRequestFamilyHouseAnnouncementDto, familyHouseAnnouncement
        );


        familyHouseAnnouncementRepository.update(familyHouseAnnouncement);
    }

    private void setFamilyHouseAnnouncementById(
            FamilyHouseAnnouncement familyHouseAnnouncement, Long familyHousePropertyId
    ) {
        FamilyHouseProperty familyHouseProperty = familyHousePropertyRepository.findById(familyHousePropertyId);
        EntityHelper.checkEntityOnNull(familyHouseProperty, FamilyHouseProperty.class, familyHousePropertyId);

        familyHouseAnnouncement.setProperty(familyHouseProperty);
    }

}
