package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.FamilyHousePropertyDto;
import ru.senla.realestatemarket.dto.property.RequestFamilyHousePropertyDto;
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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class FamilyHousePropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<FamilyHouseProperty, FamilyHousePropertyDto>
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
    @Transactional
    public void add(RequestFamilyHousePropertyDto requestFamilyHousePropertyDto, Long userId) {
        FamilyHouseProperty familyHouseProperty = familyHousePropertyMapper.toFamilyHouseProperty(requestFamilyHousePropertyDto);


        Long familyHouseId = requestFamilyHousePropertyDto.getFamilyHouseId();
        FamilyHouse familyHouse = familyHouseRepository.findById(familyHouseId);
        EntityHelper.checkEntityOnNullAfterFoundById(familyHouse, FamilyHouse.class, familyHouseId);

        familyHouseProperty.setFamilyHouse(familyHouse);


        Long renovationTypeId = requestFamilyHousePropertyDto.getRenovationTypeId();
        RenovationType renovationType = renovationTypeRepository.findById(renovationTypeId);
        EntityHelper.checkEntityOnNullAfterFoundById(renovationType, RenovationType.class, renovationTypeId);

        familyHouseProperty.setRenovationType(renovationType);


        User owner = userRepository.findById(userId);
        EntityHelper.checkEntityOnNullAfterFoundById(owner, User.class, null);

        familyHouseProperty.setOwner(owner);


        familyHousePropertyRepository.create(familyHouseProperty);
    }
    
}
