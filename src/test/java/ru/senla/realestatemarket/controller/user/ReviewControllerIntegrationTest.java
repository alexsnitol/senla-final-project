package ru.senla.realestatemarket.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.senla.realestatemarket.AbstractTestcontainersIntegrationTest;
import ru.senla.realestatemarket.dto.user.RequestReviewDto;
import ru.senla.realestatemarket.model.user.Review;
import ru.senla.realestatemarket.model.user.User;
import ru.senla.realestatemarket.service.user.IReviewService;
import ru.senla.realestatemarket.service.user.IUserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.senla.realestatemarket.config.TestConfig.BASIC_URL;

/**
 * @author Alexander Slotin (<a href="https://github.com/alexsnitol">@alexsnitol</a>) <p>
 * 2022 Nov
 */
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Sql(value = "classpath:postgresql_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReviewControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    IReviewService reviewService;
    @Autowired
    IUserService userService;


    void addTestUser(String username) throws Exception {
        User user = new User();

        user.setUsername(username);
        user.setPassword("2PEZclSv@75bczqj");
        user.setLastName("test");
        user.setFirstName("test");
        user.setPatronymic("test");
        user.setEmail("test@test.com");
        user.setPhoneNumber("80000000000");

        mvc.perform(post(BASIC_URL + "/users")
                .contentType(APPLICATION_JSON)
                .content(om.writeValueAsString(user))
                .accept(APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }

    void sendTestReview(Long userIdOfCustomer, Long userIdOfSeller, Short note) {
        Review review = new Review();
        review.setComment("comment");
        review.setNote(note);

        reviewService.sendReview(review, userIdOfCustomer, userIdOfSeller);
    }

    @BeforeEach
    void init() throws Exception {
        addTestUser("user100");
        addTestUser("user101");
        addTestUser("user102");
    }


    @Test
    void whenGetRequestInReviewsByUserIdOfSeller_thenGetAllReviewsOfItUser() throws Exception {
        sendTestReview(100L, 102L, (short) 5);
        sendTestReview(101L, 102L, (short) 5);

        mvc.perform(get(BASIC_URL + "/reviews/sellers/102")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void whenGetRequestInReviewsByCurrentSeller_thenGetAllReviewsOfItUser() throws Exception {
        sendTestReview(100L, 102L, (short) 5);
        sendTestReview(101L, 102L, (short) 5);

        authorizeUser(102L);

        mvc.perform(get(BASIC_URL + "/reviews/sellers/current")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void givenReviewWithoutNote_whenPostRequestInReviewsOfSellerId_thenAddItReview() throws Exception {
        authorizeUser(100L);

        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setComment("comment");
        requestReviewDto.setNote((short) 5);

        mvc.perform(post(BASIC_URL + "/reviews/sellers/102")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestReviewDto))
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        assertThat(reviewService.getAllBySellerId(102L)).hasSize(1);

        SecurityContextHolder.clearContext();
    }

    @Test
    void givenReviewWithNoteFive_whenPostRequestInReviewsOfSellerId_thenAddItReviewAndRatingUserWillBeFive() throws Exception {
        authorizeUser(100L);

        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setComment("comment");
        requestReviewDto.setNote((short) 5);

        mvc.perform(post(BASIC_URL + "/reviews/sellers/102")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestReviewDto))
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        assertThat(reviewService.getAllBySellerId(102L)).hasSize(1);
        assertThat(userService.getById(102L).getRating()).isEqualTo(5);
    }

    @Test
    void givenTwoReviewsWithNote_whenPostRequestInReviewsOfSellerId_thenAddItReviewAndRatingUserWillBeAvg() throws Exception {
        authorizeUser(100L);

        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setComment("comment");
        requestReviewDto.setNote((short) 5);

        mvc.perform(post(BASIC_URL + "/reviews/sellers/102")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestReviewDto))
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        authorizeUser(101L);

        requestReviewDto = new RequestReviewDto();
        requestReviewDto.setComment("comment");
        requestReviewDto.setNote((short) 2);

        mvc.perform(post(BASIC_URL + "/reviews/sellers/102")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestReviewDto))
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        assertThat(reviewService.getAllBySellerId(102L)).hasSize(2);
        assertThat(userService.getById(102L).getRating()).isEqualTo((5f+2f)/2f);
    }

    @Test
    void whenGetRequestInReviewsOfCustomersById_thenGetAllReviewsSendByCustomer() throws Exception {
        sendTestReview(100L, 101L, (short) 5);
        sendTestReview(100L, 102L, (short) 5);

        mvc.perform(get(BASIC_URL + "/reviews/customers/100")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void whenGetRequestInReviewsOfCurrentCustomers_thenGetAllReviewsSendByCurrentCustomer() throws Exception {
        sendTestReview(100L, 101L, (short) 5);
        sendTestReview(100L, 102L, (short) 5);

        authorizeUser(100L);

        mvc.perform(get(BASIC_URL + "/reviews/customers/current")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
