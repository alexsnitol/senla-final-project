package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.property.IAbstractHousingPropertyService;

@Slf4j
public abstract class AbstractHousingPropertyServiceImpl<M extends IModel<Long>> extends AbstractPropertyServiceImpl<M>
        implements IAbstractHousingPropertyService<M> {

    protected final IRenovationTypeRepository renovationTypeRepository;

    protected AbstractHousingPropertyServiceImpl(IRenovationTypeRepository renovationTypeRepository,
                                                 IUserRepository userRepository) {
        super(userRepository);
        this.renovationTypeRepository = renovationTypeRepository;
    }

}
