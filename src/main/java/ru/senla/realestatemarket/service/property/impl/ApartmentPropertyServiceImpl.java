package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestApartmentPropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.exception.ApartmentPropertyWithSpecifiedApartmentHouseAndApartmentNumberIsExistException;
import ru.senla.realestatemarket.exception.PropertySpecificOwnerIsDifferentFromRequestedOwnerException;
import ru.senla.realestatemarket.mapper.property.ApartmentPropertyMapper;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.property.ApartmentProperty;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.property.specification.ApartmentPropertySpecification.hasApartmentHouseId;
import static ru.senla.realestatemarket.repo.property.specification.ApartmentPropertySpecification.hasApartmentNumber;

@Slf4j
@Service
public class ApartmentPropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<ApartmentProperty>
        implements IApartmentPropertyService {

    private final IApartmentPropertyRepository apartmentPropertyRepository;
    private final IApartmentHouseRepository apartmentHouseRepository;

    private final ApartmentPropertyMapper apartmentPropertyMapper = Mappers.getMapper(ApartmentPropertyMapper.class);


    public ApartmentPropertyServiceImpl(IRenovationTypeRepository renovationTypeRepository,
                                        IApartmentPropertyRepository apartmentPropertyRepository,
                                        IApartmentHouseRepository apartmentHouseRepository,
                                        IUserRepository userRepository) {
        super(renovationTypeRepository, userRepository);
        this.apartmentPropertyRepository = apartmentPropertyRepository;
        this.apartmentHouseRepository = apartmentHouseRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(apartmentPropertyRepository);
        setClazz(ApartmentProperty.class);
    }


    @Override
    public List<ApartmentPropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<ApartmentProperty> apartmentPropertyList = getAll(rsqlQuery, sortQuery);
        return apartmentPropertyMapper.toApartmentPropertyDto(apartmentPropertyList);
    }

    @Override
    public ApartmentPropertyDto getDtoById(Long id) {
        return apartmentPropertyMapper.toApartmentPropertyDto(getById(id));
    }

    @Override
    public ApartmentPropertyDto getDtoByIdOfCurrentUser(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void add(RequestApartmentPropertyWithUserIdOfOwnerDto requestApartmentPropertyWithUserIdOfOwnerDto) {
        Long userIdOfOwner = requestApartmentPropertyWithUserIdOfOwnerDto.getUserIdOfOwner();

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, userIdOfOwner);

        add(requestApartmentPropertyWithUserIdOfOwnerDto, userIdOfOwner);
    }

    @Override
    @Transactional
    public void addFromCurrentUser(RequestApartmentPropertyDto requestApartmentPropertyDto) {
        add(requestApartmentPropertyDto, UserUtil.getCurrentUserId());
    }

    private void add(RequestApartmentPropertyDto requestApartmentPropertyDto, Long userIdOfOwner) {
        ApartmentProperty apartmentProperty = apartmentPropertyMapper.toApartmentProperty(requestApartmentPropertyDto);

        Long apartmentHouseId = requestApartmentPropertyDto.getApartmentHouseId();
        String apartmentNumber = requestApartmentPropertyDto.getApartmentNumber();
        checkOnExistApartmentPropertyWithItApartmentHouseIdAndApartmentNumber(apartmentHouseId, apartmentNumber);
        setApartmentHouseById(apartmentProperty, apartmentHouseId);

        Long renovationTypeId = requestApartmentPropertyDto.getRenovationTypeId();
        setRenovationTypeById(apartmentProperty, renovationTypeId);

        setOwnerByUserId(apartmentProperty, userIdOfOwner);


        apartmentPropertyRepository.create(apartmentProperty);
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
    public void updateById(
            UpdateRequestApartmentPropertyWithUserIdOfOwnerDto updateRequestApartmentPropertyWithUserIdOfOwnerDto,
            Long id
    ) {
        ApartmentProperty apartmentProperty = getById(id);

        Long userOfOwnerId = updateRequestApartmentPropertyWithUserIdOfOwnerDto.getUserIdOfOwner();
        if (userOfOwnerId != null) {
            setOwnerByUserId(apartmentProperty, userOfOwnerId);
        }

        updateFromDto(updateRequestApartmentPropertyWithUserIdOfOwnerDto, apartmentProperty);
    }

    @Override
    public void updateByIdOfCurrentUser(UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto, Long id) {
        ApartmentProperty apartmentProperty = getById(id);
        validateAccessCurrentUserToApartmentProperty(apartmentProperty);

        updateFromDto(updateRequestApartmentPropertyDto, apartmentProperty);
    }

    private static void validateAccessCurrentUserToApartmentProperty(ApartmentProperty apartmentProperty) {
        if (apartmentProperty.getOwner().getId().equals(UserUtil.getCurrentUserId())) {
            String message = "Access denied, because owner of it apartment property is different from requested owner";

            log.error(message);
            throw new PropertySpecificOwnerIsDifferentFromRequestedOwnerException(message);
        }
    }

    private void updateFromDto(UpdateRequestApartmentPropertyDto updateRequestApartmentPropertyDto,
                               ApartmentProperty apartmentProperty
    ) {
        Long apartmentHouseId = updateRequestApartmentPropertyDto.getApartmentHouseId();
        String apartmentNumber = updateRequestApartmentPropertyDto.getApartmentNumber();
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

        Long renovationTypeId = updateRequestApartmentPropertyDto.getRenovationTypeId();
        if (renovationTypeId != null) {
            setRenovationTypeById(apartmentProperty, renovationTypeId);
        }

        apartmentPropertyMapper.updateApartmentPropertyFromUpdateRequestApartmentPropertyDto(
                updateRequestApartmentPropertyDto, apartmentProperty
        );

        apartmentPropertyRepository.update(apartmentProperty);
    }

    @Override
    @Transactional
    public void setDeletedStatusByIdOfCurrentUser(Long id) {
        ApartmentProperty apartmentProperty = getById(id);

        // TODO
    }

    private void setOwnerByUserId(ApartmentProperty apartmentProperty, Long userOfOwnerId) {
        User owner = userRepository.findById(userOfOwnerId);
        EntityHelper.checkEntityOnNull(owner, User.class, userOfOwnerId);

        apartmentProperty.setOwner(owner);
    }

    private void setRenovationTypeById(ApartmentProperty apartmentProperty, Long renovationTypeId) {
        RenovationType renovationType = renovationTypeRepository.findById(renovationTypeId);
        EntityHelper.checkEntityOnNull(renovationType, RenovationType.class, renovationTypeId);

        apartmentProperty.setRenovationType(renovationType);
    }

    private void setApartmentHouseById(ApartmentProperty apartmentProperty, Long apartmentHouseId) {
        ApartmentHouse apartmentHouse = apartmentHouseRepository.findById(apartmentHouseId);
        EntityHelper.checkEntityOnNull(apartmentHouse, ApartmentHouse.class, apartmentHouseId);

        apartmentProperty.setApartmentHouse(apartmentHouse);
    }

    @Override
    public List<ApartmentPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<ApartmentProperty> apartmentPropertyList
                = apartmentPropertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return apartmentPropertyMapper.toApartmentPropertyDto(apartmentPropertyList);
    }

}
