package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.ApartmentPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestApartmentPropertyDto;
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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ApartmentPropertyServiceImpl
        extends AbstractHousingPropertyServiceImpl<ApartmentProperty, ApartmentPropertyDto>
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
    @Transactional
    public void add(RequestApartmentPropertyDto requestApartmentPropertyDto, Long userId) {
        ApartmentProperty apartmentProperty = apartmentPropertyMapper.toApartmentProperty(requestApartmentPropertyDto);


        Long apartmentHouseId = requestApartmentPropertyDto.getApartmentHouseId();
        ApartmentHouse apartmentHouse = apartmentHouseRepository.findById(apartmentHouseId);
        EntityHelper.checkEntityOnNullAfterFoundById(apartmentHouse, ApartmentHouse.class, apartmentHouseId);

        apartmentProperty.setApartmentHouse(apartmentHouse);


        Long renovationTypeId = requestApartmentPropertyDto.getRenovationTypeId();
        RenovationType renovationType = renovationTypeRepository.findById(renovationTypeId);
        EntityHelper.checkEntityOnNullAfterFoundById(renovationType, RenovationType.class, renovationTypeId);

        apartmentProperty.setRenovationType(renovationType);


        User owner = userRepository.findById(userId);
        EntityHelper.checkEntityOnNullAfterFoundById(owner, User.class, null);

        apartmentProperty.setOwner(owner);


        apartmentPropertyRepository.create(apartmentProperty);
    }

}
