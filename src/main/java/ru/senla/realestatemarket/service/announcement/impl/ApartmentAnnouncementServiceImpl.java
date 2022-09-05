package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.exception.PropertySpecificOwnerIsDifferentFromRequestedOwnerException;
import ru.senla.realestatemarket.mapper.announcement.ApartmentAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort;
import ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification;
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;
import ru.senla.realestatemarket.service.announcement.IApartmentAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification.hasUserIdOfOwnerInProperty;

@Slf4j
@Service
public class ApartmentAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<ApartmentAnnouncement>
        implements IApartmentAnnouncementService {

    private final IApartmentAnnouncementRepository apartmentAnnouncementRepository;
    private final IApartmentPropertyRepository apartmentPropertyRepository;

    private final ApartmentAnnouncementMapper apartmentAnnouncementMapper
            = Mappers.getMapper(ApartmentAnnouncementMapper.class);


    public ApartmentAnnouncementServiceImpl(IApartmentAnnouncementRepository apartmentAnnouncementRepository,
                                            IApartmentPropertyRepository apartmentPropertyRepository) {
        this.apartmentAnnouncementRepository = apartmentAnnouncementRepository;
        this.apartmentPropertyRepository = apartmentPropertyRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentAnnouncementRepository);
        setClazz(ApartmentAnnouncement.class);
    }


    @Override
    @Transactional
    public List<ApartmentAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<ApartmentAnnouncement> apartmentAnnouncementList;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementList = getAll(rsqlQuery,
                    AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            apartmentAnnouncementList = getAll(rsqlQuery, sortQuery);
        }

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncementList);
    }

    @Override
    public List<ApartmentAnnouncementDto> getAllWithOpenStatusDto(String rsqlQuery, String sortQuery) {
        List<ApartmentAnnouncement> apartmentAnnouncementList;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            apartmentAnnouncementList = getAll(
                    GenericAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN)),
                    rsqlQuery,
                    sortQuery);
        }

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncementList);
    }

    @Override
    public List<ApartmentAnnouncementDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        List<ApartmentAnnouncement> apartmentAnnouncementList;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementList = getAll(hasUserIdOfOwnerInProperty(UserUtil.getCurrentUserId()),
                    rsqlQuery, AnnouncementSort.byCreatedDtAsc());
        } else {
            apartmentAnnouncementList = getAll(hasUserIdOfOwnerInProperty(UserUtil.getCurrentUserId()),
                    rsqlQuery, sortQuery);
        }

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncementList);
    }

    @Override
    public ApartmentAnnouncementDto getDtoById(Long id) {
        ApartmentAnnouncement apartmentAnnouncement = getById(id);
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncement);
    }

    @Override
    public ApartmentAnnouncementDto getByIdWithOpenStatusDto(Long id) {
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(
                getOne(ApartmentAnnouncementSpecification.hasId(id).and(
                        ApartmentAnnouncementSpecification.hasStatuses(List.of(AnnouncementStatusEnum.OPEN))))
        );
    }

    @Override
    public ApartmentAnnouncementDto getByIdDtoOfCurrentUser(Long id) {
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(
                getByIdAndCurrentUserId(id)
        );
    }

    @Override
    @Transactional
    public void addFromDto(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto) {
        ApartmentAnnouncement apartmentAnnouncement
                = apartmentAnnouncementMapper.toApartmentAnnouncement(requestApartmentAnnouncementDto);

        Long apartmentPropertyId = requestApartmentAnnouncementDto.getApartmentPropertyId();
        setApartmentPropertyById(apartmentAnnouncement, apartmentPropertyId);

        apartmentAnnouncementRepository.create(apartmentAnnouncement);
    }

    @Override
    @Transactional
    public void addFromCurrentUser(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto) {
        Long apartmentPropertyId = requestApartmentAnnouncementDto.getApartmentPropertyId();

        validateAccessCurrentUserToApartmentPropertyById(apartmentPropertyId);

        addFromDto(requestApartmentAnnouncementDto);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto, Long id) {
        ApartmentAnnouncement apartmentAnnouncement = getById(id);

        updateFromDto(updateRequestApartmentAnnouncementDto, apartmentAnnouncement);
    }

    @Override
    @Transactional
    public void updateByIdFromCurrentUser(UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto,
                                          Long id
    ) {
        ApartmentAnnouncement apartmentAnnouncement = getByIdAndCurrentUserId(id);

        Long apartmentPropertyIdOfUpdateRequest = updateRequestApartmentAnnouncementDto.getApartmentPropertyId();
        validateAccessCurrentUserToApartmentPropertyById(apartmentPropertyIdOfUpdateRequest);

        updateFromDto(updateRequestApartmentAnnouncementDto, apartmentAnnouncement);
    }

    private void updateFromDto(UpdateRequestApartmentAnnouncementDto updateRequestApartmentAnnouncementDto, ApartmentAnnouncement apartmentAnnouncement) {
        Long apartmentPropertyId = updateRequestApartmentAnnouncementDto.getApartmentPropertyId();
        if (apartmentPropertyId != null) {
            setApartmentPropertyById(apartmentAnnouncement, apartmentPropertyId);
        }


        HousingAnnouncementTypeEnum announcementType = updateRequestApartmentAnnouncementDto.getType();
        AnnouncementStatusEnum announcementStatus = updateRequestApartmentAnnouncementDto.getStatus();

        validateAnnouncementTypeOnAccordanceWithStatusIfItNotNull(apartmentAnnouncement,
                announcementType, announcementStatus);


        apartmentAnnouncementMapper.updateApartmentAnnouncementFromUpdateRequestApartmentAnnouncementDto(
                updateRequestApartmentAnnouncementDto, apartmentAnnouncement
        );


        apartmentAnnouncementRepository.update(apartmentAnnouncement);
    }

    private ApartmentAnnouncement getByIdAndCurrentUserId(Long id) {
        return getOne(ApartmentAnnouncementSpecification.hasId(id)
                .and(ApartmentAnnouncementSpecification.hasUserIdOfOwnerInProperty(
                        UserUtil.getCurrentUserId())));
    }

    private void validateAccessCurrentUserToApartmentPropertyById(Long apartmentPropertyId) {
        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        Long userIdOfOwner = apartmentProperty.getOwner().getId();

        if (!Objects.equals(userIdOfOwner, UserUtil.getCurrentUserId())) {
            String message = String.format(
                    "Access denied, because property by id %s owns another user.", userIdOfOwner);

            log.error(message);
            throw new PropertySpecificOwnerIsDifferentFromRequestedOwnerException(message);
        }
    }

    private void setApartmentPropertyById(ApartmentAnnouncement apartmentAnnouncement, Long apartmentPropertyId) {
        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        EntityHelper.checkEntityOnNull(apartmentProperty, ApartmentProperty.class, apartmentPropertyId);

        apartmentAnnouncement.setProperty(apartmentProperty);
    }

    @Override
    @Transactional
    public void setDeletedStatusByIdAndUpdate(Long id) {
        ApartmentAnnouncement apartmentAnnouncement = getById(id);
        
        setDeletedStatus(apartmentAnnouncement);
        
        apartmentAnnouncementRepository.update(apartmentAnnouncement);
    }
}
