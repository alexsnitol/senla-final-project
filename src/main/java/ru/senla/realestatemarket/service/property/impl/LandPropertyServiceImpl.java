package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.mapper.property.LandPropertyMapper;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.property.LandProperty;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.ILandPropertyService;
import ru.senla.realestatemarket.util.SortUtil;
import ru.senla.realestatemarket.util.UserUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class LandPropertyServiceImpl
        extends AbstractPropertyServiceImpl<LandProperty>
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
    public LandPropertyDto getDtoById(Long id) {
        return landPropertyMapper.toLandPropertyDto(getById(id));
    }

    @Override
    public void add(RequestLandPropertyDto requestLandPropertyDto, Long userIdOfOwner) {
        LandProperty landProperty = landPropertyMapper.toLandProperty(requestLandPropertyDto);

        Long streetId = requestLandPropertyDto.getStreetId();
        setStreetById(landProperty, streetId);

        User owner = userRepository.findById(userIdOfOwner);
        EntityHelper.checkEntityOnNull(owner, User.class, null);

        landProperty.setOwner(owner);


        landPropertyRepository.create(landProperty);
    }

    @Override
    @Transactional
    public void addFromCurrentUser(RequestLandPropertyDto requestLandPropertyDto) {
        add(requestLandPropertyDto, UserUtil.getCurrentUserId());
    }

    private void setStreetById(LandProperty landProperty, Long streetId) {
        Street street = streetRepository.findById(streetId);
        EntityHelper.checkEntityOnNull(street, Street.class, streetId);

        landProperty.setStreet(street);
    }

    @Override
    @Transactional
    public void updateById(UpdateRequestLandPropertyDto updateRequestLandPropertyDto, Long id) {
        LandProperty landProperty = getById(id);

        Long streetId = updateRequestLandPropertyDto.getStreetId();
        if (streetId != null) {
            setStreetById(landProperty, streetId);
        }

        landPropertyMapper.updateLandPropertyFromUpdateRequestLandPropertyDto(
                updateRequestLandPropertyDto, landProperty
        );


        landPropertyRepository.update(landProperty);
    }

    @Override
    public List<LandPropertyDto> getAllDtoOfCurrentUser(String rsqlQuery, String sortQuery) {
        Sort sort = SortUtil.parseSortQuery(sortQuery);

        List<LandProperty> landPropertyList
                = landPropertyRepository.findAllByUserIdOfOwner(UserUtil.getCurrentUserId(), rsqlQuery, sort);

        return landPropertyMapper.toLandPropertyDto(landPropertyList);
    }
}
