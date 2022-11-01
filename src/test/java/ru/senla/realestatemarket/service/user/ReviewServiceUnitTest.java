package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.senla.realestatemarket.config.TestConfig;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.mapper.user.ReviewMapper;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IReviewRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.util.UserUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
class ReviewServiceUnitTest {

    @Autowired
    IReviewService reviewService;
    @Autowired
    IReviewRepository mockedReviewRepository;
    @Autowired
    IUserRepository mockedUserRepository;
    @Autowired
    UserUtil mockedUserUtil;
    
    @Autowired
    ReviewMapper mockedReviewMapper;

    Review mockedReview = mock(Review.class);
    ReviewDto mockedReviewDto = mock(ReviewDto.class);
    RequestReviewDto mockedRequestReviewDto = mock(RequestReviewDto.class);
    User mockedUser = mock(User.class);


    @AfterEach
    void clearInvocationsInMocked() {
        Mockito.clearInvocations(
                mockedReviewRepository,
                mockedUserRepository,
                mockedUserUtil
        );
    }


    @Test
    void whenGetAllBySellerIdCalled_ThenFindBySellerIdAndReturnListOfReviews() {
        // test review list
        List<Review> testReviewList = List.of(mockedReview);

        // expected review list
        List<Review> expectedReviewList = List.of(mockedReview);


        // test
        when(mockedReviewRepository.findAllBySellerId(eq(1L), any())).thenReturn(testReviewList);

        List<Review> result = reviewService.getAllBySellerId(1L);

        assertEquals(expectedReviewList, result);
    }

    @Test
    void whenGetAllDtoBySellerIdCalled_ThenFindBySellerIdAndReturnListOfUnmappedReviews() {
        // test review list
        List<Review> testReviewList = List.of(mockedReview);

        // expected review list
        List<ReviewDto> expectedReviewList = List.of(mockedReviewDto);
        
        
        // test
        when(mockedReviewRepository.findAllBySellerId(eq(1L), any())).thenReturn(testReviewList);
        when(mockedReviewMapper.toReviewDto(testReviewList)).thenReturn(expectedReviewList);
        
        List<ReviewDto> result = reviewService.getAllDtoBySellerId(1L);

        assertEquals(expectedReviewList, result);
    }

    @Test
    void whenGetAllByCustomerIdCalled_ThenFindByCustomerIdAndReturnListOfReviews() {
        // test review list
        List<Review> testReviewList = List.of(mockedReview);

        // expected review list
        List<Review> expectedReviewList = List.of(mockedReview);


        // test
        when(mockedReviewRepository.findAllByCustomerId(eq(1L), any())).thenReturn(testReviewList);

        List<Review> result = reviewService.getAllByCustomerId(1L);

        assertEquals(expectedReviewList, result);
    }

    @Test
    void whenGetAllDtoByCustomerIdCalled_ThenFindByCustomerIdAndReturnListOfUnmappedReviews() {
        // test review list
        List<Review> testReviewList = List.of(mockedReview);

        // expected review list
        List<ReviewDto> expectedReviewList = List.of(mockedReviewDto);


        // test
        when(mockedReviewRepository.findAllByCustomerId(eq(1L), any())).thenReturn(testReviewList);
        when(mockedReviewMapper.toReviewDto(testReviewList)).thenReturn(expectedReviewList);

        List<ReviewDto> result = reviewService.getAllDtoByCustomerId(1L);

        assertEquals(expectedReviewList, result);
    }

    @Test
    void whenSendReviewCalled_ThenFindBySpecifiedIdAndCreate() {
        // test customer
        User testUserOfCustomer = mockedUser;

        // test seller
        User testUserOfSeller = mock(User.class);


        // test
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfCustomer);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfSeller);

        reviewService.sendReview(mockedReview, 1L, 2L);

        verify(mockedReview, times(1)).setCustomer(testUserOfCustomer);
        verify(mockedReview, times(1)).setSeller(testUserOfSeller);
        verify(mockedReviewRepository, times(1)).create(mockedReview);
    }

    @Test
    void whenSendReviewFromCurrentUserCalledWithReviewParameter_ThenFindBySpecifiedIdAndCreate() {
        // test customer
        User testUserOfCustomer = mockedUser;

        // test seller
        User testUserOfSeller = mock(User.class);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfCustomer);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfSeller);

        reviewService.sendReview(mockedReview, 1L, 2L);

        verify(mockedReview, times(1)).setCustomer(testUserOfCustomer);
        verify(mockedReview, times(1)).setSeller(testUserOfSeller);
        verify(mockedReviewRepository, times(1)).create(mockedReview);
    }

    @Test
    void whenSendReviewFromCurrentUserCalledWithRequestReviewDtoParameter_ThenFindBySpecifiedIdAndCreate() {
        // test customer
        User testUserOfCustomer = mockedUser;

        // test seller
        User testUserOfSeller = mock(User.class);


        // test
        when(mockedUserUtil.getCurrentUserId()).thenReturn(1L);
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfCustomer);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfSeller);
        when(mockedReviewMapper.requestReviewDtoToReview(mockedRequestReviewDto)).thenReturn(mockedReview);

        reviewService.sendReview(mockedReview, 1L, 2L);

        verify(mockedReview, times(1)).setCustomer(testUserOfCustomer);
        verify(mockedReview, times(1)).setSeller(testUserOfSeller);
        verify(mockedReviewRepository, times(1)).create(mockedReview);
    }


}
