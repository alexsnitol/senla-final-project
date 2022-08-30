package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.Mapper;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.model.user.Review;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {UserMapper.class})
public abstract class ReviewMapper {

    public abstract Review requestReviewDtoToReview(RequestReviewDto requestReviewDto);

    public abstract ReviewDto toReviewDto(Review review);

    public abstract List<ReviewDto> toReviewDto(Collection<Review> reviews);

}
