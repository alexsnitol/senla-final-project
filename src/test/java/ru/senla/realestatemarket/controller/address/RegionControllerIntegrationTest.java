package ru.senla.realestatemarket.controller.address;

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
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.service.address.IRegionService;

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
class RegionControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    IRegionService regionService;


    void addTestRegion(String name) {
        Region region = new Region();
        region.setName(name);

        regionService.add(region);
    }

    void addTestRegion() {
        addTestRegion("region");
    }


    @Test
    void givenRegion_whenPostRequestInRegions_thenAddRegion() throws Exception {
        RequestRegionDto requestRegionDto = new RequestRegionDto();
        requestRegionDto.setName("region");

        mvc.perform(post(BASIC_URL + "/regions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestRegionDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));

        assertDoesNotThrow(() -> regionService.getById(100L));
    }

    @Test
    void givenRegionWithExistInDbName_whenPostRequestInRegions_thenThrowDataIntegrityViolationException() throws Exception {
        addTestRegion("existName");

        RequestRegionDto requestRegionDto = new RequestRegionDto();
        requestRegionDto.setName("existName");

        mvc.perform(post(BASIC_URL + "/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(requestRegionDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DataIntegrityViolationException"));
    }

    @Test
    void givenChangedRegion_whenPatchRequestInRegionById_thenRegionWillBeChanged() throws Exception {
        addTestRegion();

        RequestRegionDto requestRegionDto = new RequestRegionDto();
        requestRegionDto.setName("newName");

        mvc.perform(patch(BASIC_URL + "/regions/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(requestRegionDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(regionService.getById(100L).getName()).isEqualTo("newName");
    }

    @Test
    void whenDeleteRequestInRegionById_thenDeleteId() throws Exception {
        addTestRegion();

        mvc.perform(delete(BASIC_URL + "/regions/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> regionService.getById(100L));
    }

    @Test
    void whenGetRequestInRegionById_thenGetIt() throws Exception {
        addTestRegion();

        mvc.perform(get(BASIC_URL + "/regions/100")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void whenGetRequestInRegionByIdInCaseNotExistingItEntity_thenThrowEntityNotFoundException() throws Exception {
        mvc.perform(get(BASIC_URL + "/regions/100")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetRequestInRegions_thenGetItAll() throws Exception {
        addTestRegion("region1");
        addTestRegion("region2");

        mvc.perform(get(BASIC_URL + "/regions")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()
                )
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
