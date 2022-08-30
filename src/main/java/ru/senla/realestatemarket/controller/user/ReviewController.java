package ru.senla.realestatemarket.controller.user;

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
@RequestMapping("/api/")
public class ReviewController {

    private final IReviewService reviewService;


    @GetMapping("/customers/{customerId}/reviews")
    public List<ReviewDto> getReviewsByCustomerId(@PathVariable Long customerId) {
        return reviewService.getAllDtoByCustomerId(customerId);
    }

    @GetMapping("/sellers/{sellerId}/reviews")
    public List<ReviewDto> getReviewsBySellerId(@PathVariable Long sellerId) {
        return reviewService.getAllDtoBySellerId(sellerId);
    }

    @PostMapping("/sellers/{sellerId}/reviews")
    public ResponseEntity<RestResponseDto> sendReviewToSellerBySellerId(
            @RequestBody RequestReviewDto requestReviewDto,
            @PathVariable Long sellerId
    ) {
        reviewService.sendReviewFromCurrentUser(requestReviewDto, sellerId);
        return ResponseEntity.ok(new RestResponseDto("Review successful sent", HttpStatus.OK.value()));
    }

}
