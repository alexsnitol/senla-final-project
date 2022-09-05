package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestFamilyHousePropertyDto;
import ru.senla.realestatemarket.mapper.property.FamilyHousePropertyMapper;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.property.FamilyHouseProperty;
import ru.senla.realestatemarket.model.property.RenovationType;
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

@Slf4j
@Service
public class FamilyHousePropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<FamilyHouseProperty>
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
    public List<FamilyHousePropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<FamilyHouseProperty> familyHousePropertyList = getAll(rsqlQuery, sortQuery);
        return familyHousePropertyMapper.toFamilyHousePropertyDto(familyHousePropertyList);
    }

    @Override
    public FamilyHousePropertyDto getDtoById(Long id) {
        return familyHousePropertyMapper.toFamilyHousePropertyDto(getById(id));
    }

    @Override
    @Transactional
    public void add(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto, Long userIdOfOwner) {
        FamilyHouseProperty familyHouseProperty = familyHousePropertyMapper.toFamilyHouseProperty(requestFamilyHousePropertyDto);

        Long familyHouseId = requestFamilyHousePropertyDto.getFamilyHouseId();
        setFamilyHouseById(familyHouseProperty, familyHouseId);

        Long renovationTypeId = requestFamilyHousePropertyDto.getRenovationTypeId();
        setRenovationTypeById(familyHouseProperty, renovationTypeId);

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, null);

        familyHouseProperty.setOwner(owner);


        familyHousePropertyRepository.create(familyHouseProperty);
    }

    @Override
    @Transactional
    public void addFromCurrentUser(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto) {
        add(requestFamilyHousePropertyDto, UserUtil.getCurrentUserId());
    }

    private void setRenovationTypeById(FamilyHouseProperty familyHouseProperty, Long renovationTypeId) {
        RenovationType renovationType = renovationTypeRepository.findById(renovationTypeId);
        EntityHelper.checkEntityOnNull(renovationType, RenovationType.class, renovationTypeId);

        familyHouseProperty.setRenovationType(renovationType);
    }

    private void setFamilyHouseById(FamilyHouseProperty familyHouseProperty, Long familyHouseId) {
        FamilyHouse familyHouse = familyHouseRepository.findById(familyHouseId);
        EntityHelper.checkEntityOnNull(familyHouse, FamilyHouse.class, familyHouseId);

        familyHouseProperty.setFamilyHouse(familyHouse);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestFamilyHousePropertyDto updateRequestFamilyHousePropertyDto, Long id) {
        FamilyHouseProperty familyHouseProperty = getById(id);

        Long familyHouseId = updateRequestFamilyHousePropertyDto.getFamilyHouseId();
        if (familyHouseId != null) {
            setFamilyHouseById(familyHouseProperty, familyHouseId);
        }

        Long renovationTypeId = updateRequestFamilyHousePropertyDto.getRenovationTypeId();
        if (renovationTypeId != null) {
            setRenovationTypeById(familyHouseProperty, renovationTypeId);
        }

        familyHousePropertyMapper.updateFamilyHousePropertyFromUpdateRequestFamilyHouseDto(
                updateRequestFamilyHousePropertyDto, familyHouseProperty
        );


        familyHousePropertyRepository.update(familyHouseProperty);
    }

    @Override
    public List<FamilyHousePropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<FamilyHouseProperty> landPropertyList
                = familyHousePropertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return familyHousePropertyMapper.toFamilyHousePropertyDto(landPropertyList);
    }

}
