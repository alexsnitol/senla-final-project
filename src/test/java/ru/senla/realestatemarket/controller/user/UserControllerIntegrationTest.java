package ru.senla.realestatemarket.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.senla.realestatemarket.AbstractTestcontainersIntegrationTest;
import ru.senla.realestatemarket.dto.user.RequestUserDto;
import ru.senla.realestatemarket.model.user.User;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
class UserControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper om = new ObjectMapper();

    static {
        om.registerModule(new JavaTimeModule());
    }


    void addTestUser() throws Exception {
        User user = new User();

        user.setUsername("test");
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
        );
    }

    @Test
    void givenUser_whenPostInUsers_thenCreatedStatusAndMayFindItUser() throws Exception {
        User user = new User();

        user.setUsername("test");
        user.setPassword("2PEZclSv@75bczqj");
        user.setLastName("test");
        user.setFirstName("test");
        user.setPatronymic("test");
        user.setEmail("test@test.com");
        user.setPhoneNumber("80000000000");

        // test
        mvc
                .perform(post(BASIC_URL + "/users")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(user))
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));

        mvc
                .perform(get(BASIC_URL + "/users/100")
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.patronymic").value(user.getPatronymic()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(user.getPhoneNumber()));
    }

    @Test
    void whenDeleteById_thenDeletedUserNotFound() throws Exception {
        addTestUser();

        // test
        mvc.perform(delete(BASIC_URL + "/users/100")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get(BASIC_URL + "/users/100")
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void givenChangedUser_whenPatchUserById_thenUserWillChanged() throws Exception {
        addTestUser();

        RequestUserDto requestUserDto = new RequestUserDto();

        requestUserDto.setUsername("newUsername");
        requestUserDto.setEmail("newEmail@email.com");

        // test
        mvc.perform(patch(BASIC_URL + "/users/100")
                .contentType(APPLICATION_JSON)
                .content(om.writeValueAsString(requestUserDto))
                .accept(APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        mvc.perform(get(BASIC_URL + "/users/100")
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(jsonPath("$.username").value(requestUserDto.getUsername()))
                .andExpect(jsonPath("$.email").value(requestUserDto.getEmail()));
    }

}
