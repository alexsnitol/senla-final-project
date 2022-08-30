package ru.senla.realestatemarket.repo.user;

import org.springframework.data.domain.Sort;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.repo.IAbstractRepository;

import java.util.List;

public interface IReviewRepository extends IAbstractRepository<Review, Long> {

    List<Review> findAllBySellerId(Long sellerId, Sort sort);
    List<Review> findAllByCustomerId(Long customerId, Sort sort);

}
