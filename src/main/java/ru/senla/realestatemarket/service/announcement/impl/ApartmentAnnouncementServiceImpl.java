package ru.senla.realestatemarket.service.announcement.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.announcement.ApartmentAnnouncementDto;
import ru.senla.realestatemarket.dto.announcement.RequestApartmentAnnouncementDto;
import ru.senla.realestatemarket.mapper.announcement.ApartmentAnnouncementMapper;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.sort.AnnouncementSort;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;
import ru.senla.realestatemarket.service.announcement.IApartmentAnnouncementService;
import ru.senla.realestatemarket.service.helper.EntityHelper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ApartmentAnnouncementServiceImpl
        extends AbstractHousingAnnouncementServiceImpl<ApartmentAnnouncement, ApartmentAnnouncementDto>
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
            apartmentAnnouncementList = getAll(rsqlQuery, AnnouncementSort.byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc());
        } else {
            apartmentAnnouncementList = getAll(rsqlQuery, sortQuery);
        }

        return apartmentAnnouncementMapper.toApartmentAnnouncementDto(apartmentAnnouncementList);
    }

    @Override
    @Transactional
    public void add(RequestApartmentAnnouncementDto requestApartmentAnnouncementDto) {
        ApartmentAnnouncement apartmentAnnouncement
                = apartmentAnnouncementMapper.toAnnouncement(requestApartmentAnnouncementDto);


        Long apartmentPropertyId = requestApartmentAnnouncementDto.getApartmentPropertyId();
        ApartmentProperty apartmentProperty = apartmentPropertyRepository.findById(apartmentPropertyId);
        EntityHelper.checkEntityOnNullAfterFoundById(apartmentProperty, ApartmentProperty.class, apartmentPropertyId);

        apartmentAnnouncement.setProperty(apartmentProperty);


        apartmentAnnouncementRepository.create(apartmentAnnouncement);
    }

}
