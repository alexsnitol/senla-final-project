package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.model.user.Review;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {UserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ReviewMapper {

    public abstract Review requestReviewDtoToReview(RequestReviewDto requestReviewDto);

    public abstract ReviewDto toReviewDto(Review review);

    public abstract List<ReviewDto> toReviewDto(Collection<Review> reviews);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateReviewFromRequestReviewDto(
            RequestReviewDto requestReviewDto,
            @MappingTarget Review review
    );

}
