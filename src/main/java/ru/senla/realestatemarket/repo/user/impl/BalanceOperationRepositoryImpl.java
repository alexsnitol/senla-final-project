package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IBalanceOperationRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
public class BalanceOperationRepositoryImpl extends AbstractRepositoryImpl<BalanceOperation, Long> implements IBalanceOperationRepository {

    @PostConstruct
    public void init() {
        setClazz(BalanceOperation.class);
    }

}
