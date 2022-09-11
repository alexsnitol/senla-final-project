package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto;
import ru.senla.realestatemarket.exception.FamilyHousePropertyWithSpecifiedFamilyHouseIsExistException;
import ru.senla.realestatemarket.mapper.property.FamilyHousePropertyMapper;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.property.PropertyStatusEnum;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.house.IFamilyHouseRepository;
import ru.senla.realestatemarket.repo.property.IFamilyHousePropertyRepository;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.IFamilyHousePropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

import static ru.senla.realestatemarket.repo.property.specification.FamilyHousePropertySpecification.hasFamilyHouseId;
import static ru.senla.realestatemarket.repo.property.specification.FamilyHousePropertySpecification.hasIdAndUserIdOfOwner;

@Slf4j
@Service
public class FamilyHousePropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<FamilyHouseAnnouncement, FamilyHouseProperty>
        implements IFamilyHousePropertyService {

    private final IFamilyHousePropertyRepository familyHousePropertyRepository;
    private final IFamilyHouseRepository familyHouseRepository;

    private final FamilyHousePropertyMapper familyHousePropertyMapper = Mappers.getMapper(FamilyHousePropertyMapper.class);


    public FamilyHousePropertyServiceImpl(IRenovationTypeRepository renovationTypeRepository,
                                          IUserRepository userRepository,
                                          IFamilyHousePropertyRepository familyHousePropertyRepository,
                                          IFamilyHouseRepository familyHouseRepository) {
        super(renovationTypeRepository, userRepository);
        this.familyHousePropertyRepository = familyHousePropertyRepository;
        this.familyHouseRepository = familyHouseRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(familyHousePropertyRepository);
        setClazz(FamilyHouseProperty.class);
    }


    @Override
    @Transactional
    public List<FamilyHousePropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseProperty> familyHousePropertyList = getAll(rsqlQuery, sortQuery);
        return familyHousePropertyMapper.toFamilyHousePropertyDto(familyHousePropertyList);
    }

    @Override
    @Transactional
    public List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<FamilyHouseProperty> landPropertyList
                = familyHousePropertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return familyHousePropertyMapper.toFamilyHousePropertyDto(landPropertyList);
    }

    @Override
    @Transactional
    public FamilyHousePropertyDto getDtoById(Long id) {
        return familyHousePropertyMapper.toFamilyHousePropertyDto(getById(id));
    }

    @Override
    @Transactional
    public FamilyHousePropertyDto getDtoByIdOfCurrentUser(Long id) {
        return familyHousePropertyMapper.toFamilyHousePropertyDto(
                getOne(hasIdAndUserIdOfOwner(id, UserUtil.getCurrentUserId()))
        );
    }

    @Override
    @Transactional
    public void addFromDto(RequestFamilyHousePropertyWithUserIdOfOwnerDto requestDto) {
        Long userIdOfOwner = requestDto.getUserIdOfOwner();

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, userIdOfOwner);

        addFromDtoWithSpecificOwner(requestDto, userIdOfOwner);
    }

    @Override
    @Transactional
    public void addFromDtoFromCurrentUser(RequestFamilyHousePropertyDto requestDto) {
        addFromDtoWithSpecificOwner(requestDto, UserUtil.getCurrentUserId());
    }

    private void addFromDtoWithSpecificOwner(RequestFamilyHousePropertyDto requestDto, Long userIdOfOwner) {
        FamilyHouseProperty familyHouseProperty = familyHousePropertyMapper.toFamilyHouseProperty(requestDto);

        Long familyHouseId = requestDto.getFamilyHouseId();
        checkOnExistFamilyHousePropertyWithItFamilyHouseHouseId(familyHouseId);
        setFamilyHouseById(familyHouseProperty, familyHouseId);

        Long renovationTypeId = requestDto.getRenovationTypeId();
        setRenovationTypeById(familyHouseProperty, renovationTypeId);

        setOwnerByUserIdOfOwner(familyHouseProperty, userIdOfOwner);


        familyHousePropertyRepository.create(familyHouseProperty);
    }

    @Override
    @Transactional
    public void updateByIdFromDto(UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto updateRequestDto, Long id) {
        FamilyHouseProperty familyHouseProperty = getById(id);

        Long userOfOwnerId = updateRequestDto.getUserIdOfOwner();
        if (userOfOwnerId != null) {
            setOwnerByUserIdOfOwner(familyHouseProperty, userOfOwnerId);
        }

        updateFromDto(updateRequestDto, familyHouseProperty);
    }

    @Override
    @Transactional
    public void updateFromDtoByPropertyIdOfCurrentUser(UpdateRequestFamilyHousePropertyDto updateRequestDto, Long id) {
        FamilyHouseProperty familyHouseProperty = getById(id);
        validateAccessCurrentUserToProperty(familyHouseProperty);

        updateFromDto(updateRequestDto, familyHouseProperty);
    }

    private void updateFromDto(UpdateRequestFamilyHousePropertyDto updateRequestDto,
                               FamilyHouseProperty familyHouseProperty) {
        Long familyHouseId = updateRequestDto.getFamilyHouseId();
        if (familyHouseId != null) {
            checkOnExistFamilyHousePropertyWithItFamilyHouseHouseId(familyHouseId);
            setFamilyHouseById(familyHouseProperty, familyHouseId);
        }

        Long renovationTypeId = updateRequestDto.getRenovationTypeId();
        if (renovationTypeId != null) {
            setRenovationTypeById(familyHouseProperty, renovationTypeId);
        }

        PropertyStatusEnum status = updateRequestDto.getStatus();
        if (status == PropertyStatusEnum.DELETED) {
            setDeletedStatusOnPropertyAndRelatedAnnouncements(familyHouseProperty);
        }

        familyHousePropertyMapper.updateFamilyHousePropertyFromUpdateRequestFamilyHouseDto(
                updateRequestDto, familyHouseProperty
        );


        familyHousePropertyRepository.update(familyHouseProperty);
    }

    private void setFamilyHouseById(FamilyHouseProperty familyHouseProperty, Long familyHouseId) {
        FamilyHouse familyHouse = familyHouseRepository.findById(familyHouseId);
        EntityHelper.checkEntityOnNull(familyHouse, FamilyHouse.class, familyHouseId);

        familyHouseProperty.setFamilyHouse(familyHouse);
    }
    
    private void checkOnExistFamilyHousePropertyWithItFamilyHouseHouseId(
            Long familyHouseId
    ) {
        if (familyHousePropertyRepository.isExist(hasFamilyHouseId(familyHouseId))) {
            String message = String.format(
                    "Family house property with family house house with id %s is exist", familyHouseId);

            log.error(message);
            throw new FamilyHousePropertyWithSpecifiedFamilyHouseIsExistException(message);
        }
    }

}
