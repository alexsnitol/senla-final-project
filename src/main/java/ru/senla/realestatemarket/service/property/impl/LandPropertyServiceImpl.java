package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.mapper.property.LandPropertyMapper;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.repo.property.specification.GenericPropertySpecification;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.ILandPropertyService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class LandPropertyServiceImpl
        extends AbstractPropertyServiceImpl<LandAnnouncement, LandProperty>
        implements ILandPropertyService {

    private final ILandPropertyRepository landPropertyRepository;
    private final IStreetRepository streetRepository;

    private final LandPropertyMapper landPropertyMapper = Mappers.getMapper(LandPropertyMapper.class);


    public LandPropertyServiceImpl(IUserRepository userRepository,
                                   ILandPropertyRepository landPropertyRepository,
                                   IStreetRepository streetRepository) {
        super(userRepository);
        this.landPropertyRepository = landPropertyRepository;
        this.streetRepository = streetRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(landPropertyRepository);
        setClazz(LandProperty.class);
    }


    @Override
    @Transactional
    public List<LandPropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<LandProperty> landPropertyList = getAll(rsqlQuery, sortQuery);
        return landPropertyMapper.toLandPropertyDto(landPropertyList);
    }

    @Override
    @Transactional
    public List<LandPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        List<LandProperty> landPropertyList = getAll(
                GenericPropertySpecification.hasUserIdOfOwner(UserUtil.getCurrentUserId()), rsqlQuery, sortQuery);

        return landPropertyMapper.toLandPropertyDto(landPropertyList);
    }

    @Override
    @Transactional
    public LandPropertyDto getDtoById(Long id) {
        return landPropertyMapper.toLandPropertyDto(getById(id));
    }

    @Override
    @Transactional
    public LandPropertyDto getDtoByIdOfCurrentUser(Long id) {
        return landPropertyMapper.toLandPropertyDto(
                getOne(GenericPropertySpecification.hasIdAndUserIdOfOwner(id, UserUtil.getCurrentUserId())));
    }

    @Override
    @Transactional
    public void addFromDto(RequestLandPropertyWithUserIdOfOwnerDto requestDto) {
        Long userIdOfOwner = requestDto.getUserIdOfOwner();

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, userIdOfOwner);

        addFromDtoWithSpecificUserIdOfOwner(requestDto, userIdOfOwner);
    }

    @Override
    @Transactional
    public void addFromDtoFromCurrentUser(RequestLandPropertyDto requestDto) {
        addFromDtoWithSpecificUserIdOfOwner(requestDto, UserUtil.getCurrentUserId());
    }

    private void addFromDtoWithSpecificUserIdOfOwner(RequestLandPropertyDto requestDto, Long userIdOfOwner) {
        LandProperty landProperty = landPropertyMapper.toLandProperty(requestDto);

        Long streetId = requestDto.getStreetId();
        setStreetById(landProperty, streetId);

        setOwnerByUserIdOfOwner(landProperty, userIdOfOwner);


        landPropertyRepository.create(landProperty);
    }

    @Override
    @Transactional
    public void updateFromDtoById(UpdateRequestLandPropertyWithUserIdOfOwnerDto updateRequestDto, Long id) {
        LandProperty landProperty = getById(id);

        Long userOfOwnerId = updateRequestDto.getUserIdOfOwner();
        if (userOfOwnerId != null) {
            setOwnerByUserIdOfOwner(landProperty, userOfOwnerId);
        }

        updateFromDto(updateRequestDto, landProperty);
    }

    @Override
    @Transactional
    public void updateFromDtoByPropertyIdOfCurrentUser(UpdateRequestLandPropertyDto updateRequestDto, Long id) {
        LandProperty landProperty = getById(id);
        validateAccessCurrentUserToProperty(landProperty);

        updateFromDto(updateRequestDto, landProperty);
    }

    private void updateFromDto(UpdateRequestLandPropertyDto updateRequestDto, LandProperty landProperty) {
        Long streetId = updateRequestDto.getStreetId();
        if (streetId != null) {
            setStreetById(landProperty, streetId);
        }

        PropertyStatusEnum status = updateRequestDto.getStatus();
        if (status == PropertyStatusEnum.DELETED) {
            setDeletedStatusOnPropertyAndRelatedAnnouncements(landProperty);
        }

        landPropertyMapper.updateLandPropertyFromUpdateRequestLandPropertyDto(
                updateRequestDto, landProperty
        );


        landPropertyRepository.update(landProperty);
    }

    private void setStreetById(LandProperty landProperty, Long streetId) {
        Street street = streetRepository.findById(streetId);
        EntityHelper.checkEntityOnNull(street, Street.class, streetId);

        landProperty.setStreet(street);
    }
}
