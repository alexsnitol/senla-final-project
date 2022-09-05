package ru.senla.realestatemarket.repo.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.repo.AbstractRepositoryImpl;
import ru.senla.realestatemarket.repo.user.IReviewRepository;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.From;
import java.util.List;

import static ru.senla.realestatemarket.repo.user.specification.ReviewSpecification.hasCustomerId;
import static ru.senla.realestatemarket.repo.user.specification.ReviewSpecification.hasSellerId;

@Slf4j
@Repository
public class ReviewRepositoryImpl extends AbstractRepositoryImpl<Review, Long> implements IReviewRepository {

    @PostConstruct
    public void init() {
        setClazz(Review.class);
    }


    @Override
    public List<Review> findAllBySellerId(Long sellerId, Sort sort) {
        return findAll(hasSellerId(sellerId), sort);
    }

    @Override
    public List<Review> findAllByCustomerId(Long customerId, Sort sort) {
        return findAll(hasCustomerId(customerId), sort);
    }

}
