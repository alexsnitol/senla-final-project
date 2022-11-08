package ru.senla.realestatemarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.senla.realestatemarket.model.user.AuthorizedUser;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 * 2022 Nov
 */
public abstract class AbstractTestcontainersIntegrationTest {

    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;

    protected static final ObjectMapper om = new ObjectMapper();

    static {
        om.registerModule(new JavaTimeModule());

        POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:10.18-alpine"));
        POSTGRES_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }

    protected static void authorizeUser(Long userId) {
        AuthorizedUser authorizedUser = new AuthorizedUser(
                userId,
                "user",
                "password", true, true, true, true,
                List.of(new SimpleGrantedAuthority("ADMIN"))
        );

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        authorizedUser,
                        null,
                        authorizedUser.getAuthorities()
                )
        );
    }

}
