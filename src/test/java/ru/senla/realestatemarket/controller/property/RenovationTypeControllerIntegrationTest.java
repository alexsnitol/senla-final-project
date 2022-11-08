package ru.senla.realestatemarket.controller.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.senla.realestatemarket.AbstractTestcontainersIntegrationTest;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "classpath:postgresql_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RenovationTypeControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    IRenovationTypeService renovationTypeService;


    void addTestRenovationType(String name) {
        RenovationType renovationType = new RenovationType();
        renovationType.setName(name);

        renovationTypeService.add(renovationType);
    }

    void addTestRenovationType() {
        addTestRenovationType("renovationType");
    }


    @Test
    void givenRenovationType_whenPostRequestInRenovationTypes_thenAddRenovationType() throws Exception {
        RequestRenovationTypeDto requestRenovationTypeDto = new RequestRenovationTypeDto();
        requestRenovationTypeDto.setName("renovationType");

        mvc.perform(post(BASIC_URL + "/renovation-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestRenovationTypeDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));

        assertDoesNotThrow(() -> renovationTypeService.getById(100L));
    }

    @Test
    void givenRenovationTypeWithExistInDbName_whenPostRequestInRenovationTypes_thenThrowDataIntegrityViolationException() throws Exception {
        addTestRenovationType("existName");

        RequestRenovationTypeDto requestRenovationTypeDto = new RequestRenovationTypeDto();
        requestRenovationTypeDto.setName("existName");

        mvc.perform(post(BASIC_URL + "/renovation-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestRenovationTypeDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DataIntegrityViolationException"));
    }

    @Test
    void givenChangedRenovationType_whenPatchRequestInRenovationTypeById_thenRenovationTypeWillBeChanged() throws Exception {
        addTestRenovationType();

        RequestRenovationTypeDto requestRenovationTypeDto = new RequestRenovationTypeDto();
        requestRenovationTypeDto.setName("newName");

        mvc.perform(patch(BASIC_URL + "/renovation-types/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestRenovationTypeDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(renovationTypeService.getById(100L).getName()).isEqualTo("newName");
    }

    @Test
    void whenDeleteRequestInRenovationTypeById_thenDeleteId() throws Exception {
        addTestRenovationType();

        mvc.perform(delete(BASIC_URL + "/renovation-types/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> renovationTypeService.getById(100L));
    }

    @Test
    void whenGetRequestInRenovationTypeById_thenGetIt() throws Exception {
        addTestRenovationType();

        mvc.perform(get(BASIC_URL + "/renovation-types/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void whenGetRequestInRenovationTypeByIdInCaseNotExistingItEntity_thenThrowEntityNotFoundException() throws Exception {
        mvc.perform(get(BASIC_URL + "/renovation-types/100")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetRequestInRenovationTypes_thenGetItAll() throws Exception {
        addTestRenovationType("renovationType1");
        addTestRenovationType("renovationType2");

        mvc.perform(get(BASIC_URL + "/renovation-types")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                )
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
