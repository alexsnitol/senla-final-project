package ru.senla.realestatemarket.service.user;

import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.service.IAbstractService;

import java.util.List;

public interface IReviewService extends IAbstractService<Review, Long> {

    List<Review> getAllBySellerId(Long sellerId);
    List<ReviewDto> getAllDtoBySellerId(Long sellerId);
    List<Review> getAllByCustomerId(Long customerId);
    List<ReviewDto> getAllDtoByCustomerId(Long customerId);

    void sendReview(Review review, Long customerId, Long sellerId);
    void sendReviewFromCurrentUser(Review review, Long sellerId);
    void sendReviewFromCurrentUser(RequestReviewDto requestReviewDto, Long sellerId);

}
