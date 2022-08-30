package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.FamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestFamilyHouseAnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.FamilyHouseAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort;
import ru.senla.realestatemarket.repo.property.IFamilyHousePropertyRepository;
import ru.senla.realestatemarket.service.announcement.IFamilyHouseAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class FamilyHouseAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<FamilyHouseAnnouncement, FamilyHouseAnnouncementDto>
        implements IFamilyHouseAnnouncementService {

    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final IFamilyHousePropertyRepository familyHousePropertyRepository;

    private final FamilyHouseAnnouncementMapper familyHouseAnnouncementMapper
            = Mappers.getMapper(FamilyHouseAnnouncementMapper.class);


    public FamilyHouseAnnouncementServiceImpl(IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
                                              IFamilyHousePropertyRepository familyHousePropertyRepository) {
        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.familyHousePropertyRepository = familyHousePropertyRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(familyHouseAnnouncementRepository);
        setClazz(FamilyHouseAnnouncement.class);
    }


    @Override
    @Transactional
    public List<FamilyHouseAnnouncementDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseAnnouncement> familyHouseAnnouncementList;

        if (sortQuery == null) {
            // default sort
            familyHouseAnnouncementList = getAll(rsqlQuery, AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            familyHouseAnnouncementList = getAll(rsqlQuery, sortQuery);
        }

        return familyHouseAnnouncementMapper.toFamilyHouseAnnouncementDto(familyHouseAnnouncementList);
    }

    @Override
    @Transactional
    public void add(RequestFamilyHouseAnnouncementDto requestFamilyHouseAnnouncementDto) {
        FamilyHouseAnnouncement familyHouseAnnouncement
                = familyHouseAnnouncementMapper.toFamilyHouseAnnouncement(requestFamilyHouseAnnouncementDto);


        Long familyHousePropertyId = requestFamilyHouseAnnouncementDto.getFamilyHousePropertyId();
        FamilyHouseProperty familyHouseProperty = familyHousePropertyRepository.findById(familyHousePropertyId);
        EntityHelper.checkEntityOnNullAfterFoundById(familyHouseProperty, FamilyHouseProperty.class, familyHousePropertyId);

        familyHouseAnnouncement.setProperty(familyHouseProperty);


        familyHouseAnnouncementRepository.create(familyHouseAnnouncement);
    }
}
