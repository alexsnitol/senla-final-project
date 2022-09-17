package ru.senla.realestatemarket.repo.user;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.user.BalanceOperation;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IBalanceOperationRepository extends IAbstractRepository<BalanceOperation, Long> {

    List<BalanceOperation> findAllByUserId(Long userId, String rsqlQuery, Sort sort);

}
