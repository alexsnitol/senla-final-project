package ru.senla.realestatemarket.service.property.impl;

import lombok.extern.slf4j.Slf4j;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.property.IAbstractPropertyService;

@Slf4j
public abstract class AbstractPropertyServiceImpl<M, D> extends AbstractServiceImpl<M, Long>
        implements IAbstractPropertyService<M, D> {

    protected final IUserRepository userRepository;


    protected AbstractPropertyServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
