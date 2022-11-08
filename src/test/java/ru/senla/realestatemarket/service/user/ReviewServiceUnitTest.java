package ru.senla.realestatemarket.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.dto.user.ReviewDto;
import ru.senla.realestatemarket.mapper.user.ReviewMapper;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.repo.user.IReviewRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.user.impl.ReviewServiceImpl;
import ru.senla.realestatemarket.util.UserUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ReviewServiceUnitTest {

    @InjectMocks ReviewServiceImpl reviewService;

    @Mock IReviewRepository mockedReviewRepository;
    @Mock IUserRepository mockedUserRepository;
    @Mock UserUtil mockedUserUtil;
    
    @Mock ReviewMapper mockedReviewMapper;

    @Mock Review mockedReview;
    @Mock ReviewDto mockedReviewDto;
    @Mock RequestReviewDto mockedRequestReviewDto;
    @Mock User mockedUser;


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
        when(mockedUserRepository.findById(1L)).thenReturn(testUserOfCustomer);
        when(mockedUserRepository.findById(2L)).thenReturn(testUserOfSeller);

        reviewService.sendReview(mockedReview, 1L, 2L);

        verify(mockedReview, times(1)).setCustomer(testUserOfCustomer);
        verify(mockedReview, times(1)).setSeller(testUserOfSeller);
        verify(mockedReviewRepository, times(1)).create(mockedReview);
    }


}
