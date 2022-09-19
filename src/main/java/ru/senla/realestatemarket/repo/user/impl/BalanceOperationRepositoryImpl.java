package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IBalanceOperationRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.senla.realestatemarket.repo.user.specification.BalanceOperationSpecification.hasUserId;

@Slf4j
@Repository
public class BalanceOperationRepositoryImpl extends AbstractRepositoryImpl<BalanceOperation, Long>
        implements IBalanceOperationRepository {

    @PostConstruct
    public void init() {
        setClazz(BalanceOperation.class);
    }

    @Override
    public List<BalanceOperation> findAllByUserId(Long userId, String rsqlQuery, Sort sort) {
        return findAllByQuery(
                hasUserId(userId), rsqlQuery, sort
        );
    }
}
