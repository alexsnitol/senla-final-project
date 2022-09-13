package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.UpdateRequestApartmentAnnouncementDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.senla.realestatemarket.repo.announcement.specification.ApartmentAnnouncementSpecification.hasStatuses;
import static ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification.hasIdAndUserIdOfOwnerInProperty;
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
    @Transactional
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
    @Transactional
    public List<ApartmentAnnouncementDto> getAllWithClosedStatusByUserIdOfOwnerDto(
            Long useIdOfOwner, String rsqlQuery, String sortQuery
    ) {
        List<ApartmentAnnouncement> apartmentAnnouncementList;

        if (sortQuery == null) {
            // default sort
            apartmentAnnouncementList = getAll(
                    hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            ApartmentAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    Sort.by(Sort.Direction.DESC, "closedDt"));
        } else {
            apartmentAnnouncementList = getAll(
                    hasStatuses(List.of(AnnouncementStatusEnum.CLOSED)).and(
                            ApartmentAnnouncementSpecification.hasUserIdOfOwnerInProperty(useIdOfOwner)
                    ),
                    rsqlQuery,
                    sortQuery);
        }

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncementList);
    }

    @Override
    @Transactional
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
    @Transactional
    public ApartmentAnnouncementDto getDtoById(Long id) {
        ApartmentAnnouncement apartmentAnnouncement = getById(id);
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncement);
    }

    @Override
    @Transactional
    public ApartmentAnnouncementDto getByIdWithOpenStatusDto(Long id) {
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(
                getOne(ApartmentAnnouncementSpecification.hasId(id).and(
                        hasStatuses(List.of(AnnouncementStatusEnum.OPEN))))
        );
    }

    @Override
    @Transactional
    public ApartmentAnnouncementDto getByIdDtoOfCurrentUser(Long id) {
        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(
                getOne(hasIdAndUserIdOfOwnerInProperty(id, UserUtil.getCurrentUserId()))
        );
    }

    @Override
    @Transactional
    public void addFromDto(RequestApartmentAnnouncementDto requestDto) {
        ApartmentAnnouncement apartmentAnnouncement
                = apartmentAnnouncementMapper.toApartmentAnnouncement(requestDto);

        Long apartmentPropertyId = requestDto.getApartmentPropertyId();
        setApartmentPropertyById(apartmentAnnouncement, apartmentPropertyId);

        apartmentAnnouncementRepository.create(apartmentAnnouncement);
    }

    @Override
    @Transactional
    public void addFromDtoFromCurrentUser(RequestApartmentAnnouncementDto requestDto) {
        Long apartmentPropertyId = requestDto.getApartmentPropertyId();

        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        EntityHelper.checkEntityOnNull(apartmentProperty, ApartmentProperty.class, apartmentPropertyId);

        validateAccessCurrentUserToProperty(apartmentProperty);

        addFromDto(requestDto);
    }

    @Override
    @Transactional
    public void updateFromDtoById(UpdateRequestApartmentAnnouncementDto updateRequestDto, Long id) {
        ApartmentAnnouncement apartmentAnnouncement = getById(id);

        updateFromDto(updateRequestDto, apartmentAnnouncement);
    }

    @Override
    @Transactional
    public void updateByIdFromDtoFromCurrentUser(UpdateRequestApartmentAnnouncementDto updateRequestDto,
                                                 Long id
    ) {
        ApartmentAnnouncement apartmentAnnouncement = getOne(
                hasIdAndUserIdOfOwnerInProperty(id, UserUtil.getCurrentUserId()));


        Long apartmentPropertyId = updateRequestDto.getApartmentPropertyId();
        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        EntityHelper.checkEntityOnNull(apartmentProperty, ApartmentProperty.class, apartmentPropertyId);

        validateAccessCurrentUserToProperty(apartmentProperty);


        updateFromDto(updateRequestDto, apartmentAnnouncement);
    }

    @Override
    @Transactional
    public List<ApartmentAnnouncementDto> getAllByKeyWords(String keyWords) {
        List<String> keyWordsSplit = Arrays.stream(keyWords.split(",")).collect(Collectors.toList());

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(
                apartmentAnnouncementRepository.findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit));
    }

    private void updateFromDto(
            UpdateRequestApartmentAnnouncementDto updateRequestDto, ApartmentAnnouncement apartmentAnnouncement
    ) {
        Long apartmentPropertyId = updateRequestDto.getApartmentPropertyId();
        if (apartmentPropertyId != null) {
            setApartmentPropertyById(apartmentAnnouncement, apartmentPropertyId);
        }


        HousingAnnouncementTypeEnum announcementType = updateRequestDto.getType();
        AnnouncementStatusEnum announcementStatus = updateRequestDto.getStatus();

        validateAnnouncementTypeOnAccordanceWithStatusIfItNotNull(apartmentAnnouncement,
                announcementType, announcementStatus);

        setStatusIfNotNull(apartmentAnnouncement, announcementStatus);

        apartmentAnnouncementMapper.updateApartmentAnnouncementFromUpdateRequestApartmentAnnouncementDto(
                updateRequestDto, apartmentAnnouncement
        );


        apartmentAnnouncementRepository.update(apartmentAnnouncement);
    }

    private void setApartmentPropertyById(ApartmentAnnouncement apartmentAnnouncement, Long apartmentPropertyId) {
        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        EntityHelper.checkEntityOnNull(apartmentProperty, ApartmentProperty.class, apartmentPropertyId);

        apartmentAnnouncement.setProperty(apartmentProperty);
    }

}
