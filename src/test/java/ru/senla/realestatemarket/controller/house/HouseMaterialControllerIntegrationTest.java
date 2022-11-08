package ru.senla.realestatemarket.controller.house;

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
import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;

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
class HouseMaterialControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    IHouseMaterialService houseMaterialService;


    void addTestHouseMaterial(String name) {
        HouseMaterial houseMaterial = new HouseMaterial();
        houseMaterial.setName(name);

        houseMaterialService.add(houseMaterial);
    }

    void addTestHouseMaterial() {
        addTestHouseMaterial("houseMaterial");
    }


    @Test
    void givenHouseMaterial_whenPostRequestInHouseMaterials_thenAddHouseMaterial() throws Exception {
        RequestHouseMaterialDto requestHouseMaterialDto = new RequestHouseMaterialDto();
        requestHouseMaterialDto.setName("houseMaterial");

        mvc.perform(post(BASIC_URL + "/house-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestHouseMaterialDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));

        assertDoesNotThrow(() -> houseMaterialService.getById(100L));
    }

    @Test
    void givenHouseMaterialWithExistInDbName_whenPostRequestInHouseMaterials_thenThrowDataIntegrityViolationException() throws Exception {
        addTestHouseMaterial("existName");

        RequestHouseMaterialDto requestHouseMaterialDto = new RequestHouseMaterialDto();
        requestHouseMaterialDto.setName("existName");

        mvc.perform(post(BASIC_URL + "/house-materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestHouseMaterialDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DataIntegrityViolationException"));
    }

    @Test
    void givenChangedHouseMaterial_whenPatchRequestInHouseMaterialById_thenHouseMaterialWillBeChanged() throws Exception {
        addTestHouseMaterial();

        RequestHouseMaterialDto requestHouseMaterialDto = new RequestHouseMaterialDto();
        requestHouseMaterialDto.setName("newName");

        mvc.perform(patch(BASIC_URL + "/house-materials/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestHouseMaterialDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(houseMaterialService.getById(100L).getName()).isEqualTo("newName");
    }

    @Test
    void whenDeleteRequestInHouseMaterialById_thenDeleteId() throws Exception {
        addTestHouseMaterial();

        mvc.perform(delete(BASIC_URL + "/house-materials/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> houseMaterialService.getById(100L));
    }

    @Test
    void whenGetRequestInHouseMaterialById_thenGetIt() throws Exception {
        addTestHouseMaterial();

        mvc.perform(get(BASIC_URL + "/house-materials/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void whenGetRequestInHouseMaterialByIdInCaseNotExistingItEntity_thenThrowEntityNotFoundException() throws Exception {
        mvc.perform(get(BASIC_URL + "/house-materials/100")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetRequestInHouseMaterials_thenGetItAll() throws Exception {
        addTestHouseMaterial("houseMaterial1");
        addTestHouseMaterial("houseMaterial2");

        mvc.perform(get(BASIC_URL + "/house-materials")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                )
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
