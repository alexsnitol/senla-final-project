package ru.senla.realestatemarket.repo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.senla.realestatemarket.AbstractTestcontainersIntegrationTest;
import ru.senla.realestatemarket.model.user.User;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Alexander Slotin (<a href="https://github.com/alexsnitol">@alexsnitol</a>) <p>
 * 2022 Nov
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "classpath:postgresql_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRepositoryIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Transactional
    void givenUser_whenCreate_thenSizeOfFindAllResultEqualsOne() {
        User user = new User();

        user.setUsername("test");
        user.setPassword("test");
        user.setLastName("test");
        user.setFirstName("test");
        user.setPatronymic("test");
        user.setEmail("test@test.com");
        user.setPhoneNumber("80000000000");

        userRepository.create(user);

        // test
        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    @Transactional
    void givenTwoUsers_whenCreate_thenSizeOfFindAllResultEqualsTwo() {
        User user1 = new User();

        user1.setUsername("test");
        user1.setPassword("test");
        user1.setLastName("test");
        user1.setFirstName("test");
        user1.setPatronymic("test");
        user1.setEmail("test@test.com");
        user1.setPhoneNumber("80000000000");

        userRepository.create(user1);


        User user2 = new User();

        user2.setUsername("test2");
        user2.setPassword("test2");
        user2.setLastName("test2");
        user2.setFirstName("test2");
        user2.setPatronymic("test2");
        user2.setEmail("test2@test.com");
        user2.setPhoneNumber("80000000000");

        userRepository.create(user2);

        // test
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    void givenTwoUsersWithSameUsername_whenCreate_thenThrowException() {
        User user1 = new User();

        user1.setUsername("test");
        user1.setPassword("test");
        user1.setLastName("test");
        user1.setFirstName("test");
        user1.setPatronymic("test");
        user1.setEmail("test@test.com");
        user1.setPhoneNumber("80000000000");

        userRepository.create(user1);


        User user2 = new User();

        user2.setUsername("test");
        user2.setPassword("test2");
        user2.setLastName("test2");
        user2.setFirstName("test2");
        user2.setPatronymic("test2");
        user2.setEmail("test2@test.com");
        user2.setPhoneNumber("80000000000");

        userRepository.create(user2);

        // test
        assertThrows(Exception.class, () -> userRepository.findAll());
    }

}
