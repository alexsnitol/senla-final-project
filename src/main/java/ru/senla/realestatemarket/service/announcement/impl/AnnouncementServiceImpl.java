package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.AnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.AnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.repo.announcement.IAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.specification.GenericAnnouncementSpecification;
import ru.senla.realestatemarket.service.announcement.IAnnouncementService;
import ru.senla.realestatemarket.util.SearchQueryUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class AnnouncementServiceImpl
        extends AbstractAnnouncementServiceImpl<Announcement> implements IAnnouncementService {

    private final IAnnouncementRepository announcementRepository;

    private final IApartmentAnnouncementRepository apartmentAnnouncementRepository;
    private final IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository;
    private final ILandAnnouncementRepository landAnnouncementRepository;

    private final AnnouncementMapper announcementMapper;


    public AnnouncementServiceImpl(
            IAnnouncementRepository announcementRepository,
            IApartmentAnnouncementRepository apartmentAnnouncementRepository,
            IFamilyHouseAnnouncementRepository familyHouseAnnouncementRepository,
            ILandAnnouncementRepository landAnnouncementRepository,
            UserUtil userUtil,
            AnnouncementMapper announcementMapper
    ) {
        super(userUtil);

        this.clazz = Announcement.class;
        this.defaultRepository = announcementRepository;

        this.announcementRepository = announcementRepository;
        this.apartmentAnnouncementRepository = apartmentAnnouncementRepository;
        this.familyHouseAnnouncementRepository = familyHouseAnnouncementRepository;
        this.landAnnouncementRepository = landAnnouncementRepository;
        this.announcementMapper = announcementMapper;
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

    @Override
    @Transactional
    public List<AnnouncementDto> getAllByKeyWords(String keyWords) {
        List<String> keyWordsSplit = Arrays.stream(keyWords.split(",")).collect(Collectors.toList());

        List<ApartmentAnnouncement> apartmentAnnouncements = apartmentAnnouncementRepository
                .findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<FamilyHouseAnnouncement> familyHouseAnnouncements = familyHouseAnnouncementRepository
                .findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<LandAnnouncement> landAnnouncements = landAnnouncementRepository
                .findAllInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<Announcement> finalAnnouncements = new LinkedList<>();
        finalAnnouncements.addAll(apartmentAnnouncements);
        finalAnnouncements.addAll(familyHouseAnnouncements);
        finalAnnouncements.addAll(landAnnouncements);

        Collections.shuffle(finalAnnouncements);

        return announcementMapper.toAnnouncementDtoWithMappedInheritors(finalAnnouncements);
    }

    @Override
    @Transactional
    public List<AnnouncementDto> getAllWithOpenStatusByKeyWords(String keyWords) {
        List<String> keyWordsSplit = SearchQueryUtil.getKeyWordsSpit(keyWords);

        List<ApartmentAnnouncement> apartmentAnnouncements = apartmentAnnouncementRepository
                .findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<FamilyHouseAnnouncement> familyHouseAnnouncements = familyHouseAnnouncementRepository
                .findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<LandAnnouncement> landAnnouncements = landAnnouncementRepository
                .findAllWithOpenStatusInTheTextFieldsOfWhichContainsTheKeys(keyWordsSplit);

        List<Announcement> finalAnnouncements = new LinkedList<>();
        finalAnnouncements.addAll(apartmentAnnouncements);
        finalAnnouncements.addAll(familyHouseAnnouncements);
        finalAnnouncements.addAll(landAnnouncements);

        Collections.shuffle(finalAnnouncements);

        return announcementMapper.toAnnouncementDtoWithMappedInheritors(finalAnnouncements);
    }

}
