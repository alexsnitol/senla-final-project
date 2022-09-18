package ru.senla.realestatemarket.controller.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.service.user.IReviewService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final IReviewService reviewService;


    @ApiOperation(
            value = "",
            notes = "Return all customer reviews of sellers by customerId",
            authorizations = @Authorization("ADMIN")
    )
    @GetMapping("/customers/{customerId}")
    public List<ReviewDto> getReviewsByCustomerId(@PathVariable Long customerId) {
        return reviewService.getAllDtoByCustomerId(customerId);
    }

    @ApiOperation(
            value = "",
            notes = "Returns all reviews of seller by sellerId"
    )
    @GetMapping("/sellers/{sellerId}")
    public List<ReviewDto> getReviewsBySellerId(@PathVariable Long sellerId) {
        return reviewService.getAllDtoBySellerId(sellerId);
    }

    @ApiOperation(
            value = "",
            notes = "Send a review to seller by sellerId from current user"
    )
    @PostMapping("/sellers/{sellerId}")
    public ResponseEntity<RestResponseDto> sendReviewToSellerBySellerId(
            @RequestBody RequestReviewDto requestReviewDto,
            @PathVariable Long sellerId
    ) {
        reviewService.sendReviewFromCurrentUser(requestReviewDto, sellerId);
        return new ResponseEntity<>(new RestResponseDto("Review has been sent",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
