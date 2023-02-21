package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.exception.ApartmentPropertyWithSpecifiedApartmentHouseAndApartmentNumberIsExistException;
import ru.senla.realestatemarket.mapper.property.ApartmentPropertyMapper;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.property.specification.GenericPropertySpecification;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;
import ru.senla.realestatemarket.util.UserUtil;

import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.property.specification.ApartmentPropertySpecification.hasApartmentHouseId;
import static ru.senla.realestatemarket.repo.property.specification.ApartmentPropertySpecification.hasApartmentNumber;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@Service
public class ApartmentPropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<ApartmentAnnouncement, ApartmentProperty>
        implements IApartmentPropertyService {

    private final IApartmentPropertyRepository apartmentPropertyRepository;
    private final IApartmentHouseRepository apartmentHouseRepository;

    private final ApartmentPropertyMapper apartmentPropertyMapper;


    public ApartmentPropertyServiceImpl(
            IRenovationTypeRepository renovationTypeRepository,
            IApartmentPropertyRepository apartmentPropertyRepository,
            IApartmentHouseRepository apartmentHouseRepository,
            IUserRepository userRepository,
            UserUtil userUtil,
            ApartmentPropertyMapper apartmentPropertyMapper
    ) {
        super(renovationTypeRepository, userRepository, userUtil);

        this.clazz = ApartmentProperty.class;
        this.defaultRepository = apartmentPropertyRepository;

        this.apartmentPropertyRepository = apartmentPropertyRepository;
        this.apartmentHouseRepository = apartmentHouseRepository;
        this.apartmentPropertyMapper = apartmentPropertyMapper;
    }


    @Override
    @Transactional
    public List<ApartmentPropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<ApartmentProperty> apartmentPropertyList = getAll(rsqlQuery, sortQuery);
        return apartmentPropertyMapper.toApartmentPropertyDto(apartmentPropertyList);
    }

    @Override
    @Transactional
    public List<ApartmentPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        List<ApartmentProperty> apartmentPropertyList = getAll(
                GenericPropertySpecification.hasUserIdOfOwner(userUtil.getCurrentUserId()), rsqlQuery, sortQuery);

        return apartmentPropertyMapper.toApartmentPropertyDto(apartmentPropertyList);
    }

    @Override
    @Transactional
    public ApartmentPropertyDto getDtoById(Long id) {
        return apartmentPropertyMapper.toApartmentPropertyDto(getById(id));
    }

    @Override
    @Transactional
    public ApartmentPropertyDto getDtoByIdOfCurrentUser(Long id) {
        return apartmentPropertyMapper.toApartmentPropertyDto(
                getOne(GenericPropertySpecification.hasIdAndUserIdOfOwner(id, userUtil.getCurrentUserId())));
    }

    @Override
    @Transactional
    public ApartmentPropertyDto addFromDto(RequestApartmentPropertyWithUserIdOfOwnerDto requestDto) {
        Long userIdOfOwner = requestDto.getUserIdOfOwner();

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, userIdOfOwner);

        return addFromDtoWithSpecificUserIdOfOwner(requestDto, userIdOfOwner);
    }

    @Override
    @Transactional
    public ApartmentPropertyDto addFromDtoFromCurrentUser(RequestApartmentPropertyDto requestDto) {
        return addFromDtoWithSpecificUserIdOfOwner(requestDto, userUtil.getCurrentUserId());
    }

    private ApartmentPropertyDto addFromDtoWithSpecificUserIdOfOwner(
            RequestApartmentPropertyDto requestDto,
            Long userIdOfOwner
    ) {
        ApartmentProperty apartmentProperty = apartmentPropertyMapper.toApartmentProperty(requestDto);

        Long apartmentHouseId = requestDto.getApartmentHouseId();
        String apartmentNumber = requestDto.getApartmentNumber();
        checkOnExistApartmentPropertyWithItApartmentHouseIdAndApartmentNumber(apartmentHouseId, apartmentNumber);
        setApartmentHouseById(apartmentProperty, apartmentHouseId);

        Long renovationTypeId = requestDto.getRenovationTypeId();
        setRenovationTypeById(apartmentProperty, renovationTypeId);

        setOwnerByUserIdOfOwner(apartmentProperty, userIdOfOwner);


        ApartmentProperty apartmentPropertyResponse = apartmentPropertyRepository.create(apartmentProperty);

        return apartmentPropertyMapper.toApartmentPropertyDto(apartmentPropertyResponse);
    }

    private void checkOnExistApartmentPropertyWithItApartmentHouseIdAndApartmentNumber(
            Long apartmentHouseId, String apartmentNumber
    ) {
        if (apartmentPropertyRepository.isExist(
                hasApartmentHouseId(apartmentHouseId).and(
                        hasApartmentNumber(apartmentNumber)))) {
            String message = String.format(
                    "Apartment property with apartment house with id %s and apartment number %s is exist",
                    apartmentHouseId, apartmentNumber);

            log.error(message);
            throw new ApartmentPropertyWithSpecifiedApartmentHouseAndApartmentNumberIsExistException(message);
        }
    }

    @Override
    @Transactional
    public void updateFromDtoById(UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestDto, Long id) {
        ApartmentProperty apartmentProperty = getById(id);

        Long userOfOwnerId = updateRequestDto.getUserIdOfOwner();
        if (userOfOwnerId != null) {
            setOwnerByUserIdOfOwner(apartmentProperty, userOfOwnerId);
        }

        updateFromDto(updateRequestDto, apartmentProperty);
    }

    @Override
    @Transactional
    public void updateFromDtoByPropertyIdOfCurrentUser(UpdateRequestApartmentPropertyDto updateRequestDto, Long id) {
        ApartmentProperty apartmentProperty = getById(id);
        validateAccessCurrentUserToProperty(apartmentProperty);

        updateFromDto(updateRequestDto, apartmentProperty);
    }

    private void updateFromDto(UpdateRequestApartmentPropertyDto updateRequestDto,
                               ApartmentProperty apartmentProperty
    ) {
        Long apartmentHouseId = updateRequestDto.getApartmentHouseId();
        String apartmentNumber = updateRequestDto.getApartmentNumber();
        if (apartmentHouseId != null || apartmentNumber != null) {
            if (apartmentHouseId == null) {
                apartmentHouseId = apartmentProperty.getApartmentHouse().getId();
            }

            if (apartmentNumber == null) {
                apartmentNumber = apartmentProperty.getApartmentNumber();
            }

            checkOnExistApartmentPropertyWithItApartmentHouseIdAndApartmentNumber(apartmentHouseId, apartmentNumber);
            setApartmentHouseById(apartmentProperty, apartmentHouseId);
        }

        Long renovationTypeId = updateRequestDto.getRenovationTypeId();
        if (renovationTypeId != null) {
            setRenovationTypeById(apartmentProperty, renovationTypeId);
        }

        PropertyStatusEnum status = updateRequestDto.getStatus();
        if (status == PropertyStatusEnum.DELETED) {
            setDeletedStatusOnPropertyAndRelatedAnnouncements(apartmentProperty);
        }

        apartmentPropertyMapper.updateApartmentPropertyFromUpdateRequestApartmentPropertyDto(
                updateRequestDto, apartmentProperty
        );

        apartmentPropertyRepository.update(apartmentProperty);
    }

    private void setApartmentHouseById(ApartmentProperty apartmentProperty, Long apartmentHouseId) {
        ApartmentHouse apartmentHouse = apartmentHouseRepository.findById(apartmentHouseId);
        EntityHelper.checkEntityOnNull(apartmentHouse, ApartmentHouse.class, apartmentHouseId);

        apartmentProperty.setApartmentHouse(apartmentHouse);
    }

}
