package ru.senla.realestatemarket.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.mapper.user.ReviewMapper;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IReviewRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.AbstractServiceImpl;
import ru.senla.realestatemarket.service.helper.EntityHelper;
import ru.senla.realestatemarket.service.user.IReviewService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ReviewServiceImpl extends AbstractServiceImpl<Review, Long> implements IReviewService {

    private final IReviewRepository reviewRepository;
    private final IUserRepository userRepository;

    private final ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);


    public ReviewServiceImpl(IReviewRepository reviewRepository,
                             IUserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        setDefaultRepository(reviewRepository);
        setClazz(Review.class);
    }


    private Long getCurrentUserId() {
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return authorizedUser.getId();
    }

    @Override
    public List<Review> getAllBySellerId(Long sellerId) {
        return reviewRepository.findAllBySellerId(sellerId, null);
    }

    @Override
    public List<ReviewDto> getAllDtoBySellerId(Long sellerId) {
        return reviewMapper.toReviewDto(getAllBySellerId(sellerId));
    }

    @Override
    public List<Review> getAllByCustomerId(Long customerId) {
        return reviewRepository.findAllByCustomerId(customerId, null);
    }

    @Override
    public List<ReviewDto> getAllDtoByCustomerId(Long customerId) {
        return reviewMapper.toReviewDto(getAllByCustomerId(customerId));
    }

    @Override
    @Transactional
    public void sendReview(Review review, Long customerId, Long sellerId) {
        User seller = userRepository.findById(sellerId);
        EntityHelper.checkEntityOnNullAfterFoundById(seller, User.class, sellerId);

        User customer = userRepository.findById(customerId);
        EntityHelper.checkEntityOnNullAfterFoundById(customer, User.class, customerId);

        review.setCustomer(customer);
        review.setSeller(seller);

        reviewRepository.create(review);
    }

    @Override
    @Transactional
    public void sendReviewFromCurrentUser(Review review, Long sellerId) {
        sendReview(review, getCurrentUserId(), sellerId);
    }

    @Override
    @Transactional
    public void sendReviewFromCurrentUser(RequestReviewDto requestReviewDto, Long sellerId) {
        Review review = reviewMapper.requestReviewDtoToReview(requestReviewDto);
        sendReviewFromCurrentUser(review, sellerId);
    }
}
