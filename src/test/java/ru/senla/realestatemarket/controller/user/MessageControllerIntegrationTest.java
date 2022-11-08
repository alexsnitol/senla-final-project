package ru.senla.realestatemarket.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.senla.realestatemarket.AbstractTestcontainersIntegrationTest;
import ru.senla.realestatemarket.dto.user.RequestMessageDto;
import ru.senla.realestatemarket.model.user.User;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class MessageControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;


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

    void sendTestMessage(Long userIdOfSender, Long userIdOfReceiver) throws Exception {
        authorizeUser(userIdOfSender);

        RequestMessageDto requestMessageDto = new RequestMessageDto();
        requestMessageDto.setText("test");

        mvc.perform(post(BASIC_URL + "/messages/users/" + userIdOfReceiver)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestMessageDto))
                        .accept(APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }

    @BeforeEach
    void init() throws Exception {
        addTestUser("user100");
        addTestUser("user101");
    }


    @Test
    void whenGetRequestInMessagesByUserId_thenGetAllMessages() throws Exception {
        sendTestMessage(100L, 101L);
        sendTestMessage(101L, 100L);

        authorizeUser(100L);

        mvc.perform(get(BASIC_URL + "/messages/users/101")
                        .accept(APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void whenGetRequestInMessages_thenGetAllUsersWitchWhomThereAreMessages() throws Exception {
        sendTestMessage(100L, 101L);
        sendTestMessage(101L, 100L);

        authorizeUser(100L);

        mvc.perform(get(BASIC_URL + "/messages/users")
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
