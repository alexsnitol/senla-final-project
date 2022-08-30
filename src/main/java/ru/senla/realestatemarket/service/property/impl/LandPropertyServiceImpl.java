package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.mapper.property.LandPropertyMapper;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.ILandPropertyService;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class LandPropertyServiceImpl
        extends AbstractPropertyServiceImpl<LandProperty, LandPropertyDto>
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
    public List<LandPropertyDto> getAllDto(String rsqlQuery, String sortQuery) {
        List<LandProperty> landPropertyList = getAll(rsqlQuery, sortQuery);
        return landPropertyMapper.toLandPropertyDto(landPropertyList);
    }

    @Override
    public void add(RequestLandPropertyDto requestLandPropertyDto, Long userId) {
        LandProperty landProperty = landPropertyMapper.toLandProperty(requestLandPropertyDto);


        Long streetId = requestLandPropertyDto.getAddress().getStreetId();
        Street street = streetRepository.findById(streetId);
        EntityHelper.checkEntityOnNullAfterFoundById(street, Street.class, streetId);

        landProperty.setStreet(street);


        User owner = userRepository.findById(userId);
        EntityHelper.checkEntityOnNullAfterFoundById(owner, User.class, null);

        landProperty.setOwner(owner);


        landPropertyRepository.create(landProperty);
    }
}
