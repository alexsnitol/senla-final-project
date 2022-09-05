package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.model.IModel;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IAbstractPropertyService;

@Slf4j
public abstract class AbstractPropertyServiceImpl<M extends IModel<Long>> extends AbstractServiceImpl<M, Long>
        implements IAbstractPropertyService<M> {

    protected final IUserRepository userRepository;


    protected AbstractPropertyServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
