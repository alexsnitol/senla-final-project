package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.model.announcement.Announcement;
import ru.senla.realestatemarket.model.property.HousingProperty;
import ru.senla.realestatemarket.model.property.IPropertyWithAnnouncementList;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.property.IAbstractHousingPropertyService;

@Slf4j
public abstract class AbstractHousingPropertyServiceImpl
        <A extends Announcement, P extends HousingProperty & IPropertyWithAnnouncementList<A>>
        extends AbstractPropertyServiceImpl<A, P>
        implements IAbstractHousingPropertyService<P> {

    protected final IRenovationTypeRepository renovationTypeRepository;

    protected AbstractHousingPropertyServiceImpl(IRenovationTypeRepository renovationTypeRepository,
                                                 IUserRepository userRepository) {
        super(userRepository);
        this.renovationTypeRepository = renovationTypeRepository;
    }


    protected void setRenovationTypeById(P property, Long renovationTypeId) {
        RenovationType renovationType = renovationTypeRepository.findById(renovationTypeId);
        EntityHelper.checkEntityOnNull(renovationType, RenovationType.class, renovationTypeId);

        property.setRenovationType(renovationType);
    }

}
