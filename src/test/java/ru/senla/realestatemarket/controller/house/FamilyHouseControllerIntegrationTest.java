package ru.senla.realestatemarket.controller.house;

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
import ru.senla.realestatemarket.dto.house.RequestFamilyHouseDto;
import ru.senla.realestatemarket.dto.house.UpdateRequestFamilyHouseDto;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.house.FamilyHouse;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.address.ICityService;
import ru.senla.realestatemarket.service.address.IRegionService;
import ru.senla.realestatemarket.service.address.IStreetService;
import ru.senla.realestatemarket.service.house.IFamilyHouseService;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
class FamilyHouseControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    IFamilyHouseService familyHouseService;
    @Autowired
    IHouseMaterialService houseMaterialService;
    @Autowired
    IAddressService addressService;
    @Autowired
    IRegionService regionService;
    @Autowired
    ICityService cityService;
    @Autowired
    IStreetService streetService;

    @BeforeEach
    void init() {
        HouseMaterial houseMaterial = new HouseMaterial();
        houseMaterial.setName("test");

        houseMaterialService.add(houseMaterial);


        Address address = new Address();

        Region region = new Region();
        region.setName("region");
        regionService.add(region);
        address.setRegion(region);

        City city = new City();
        city.setRegion(region);
        city.setName("city");
        cityService.add(city);
        address.setCity(city);

        Street street = new Street();
        street.setCity(city);
        street.setName("street");
        streetService.add(street);
        address.setStreet(street);

        address.setHouseNumber("test");
        addressService.add(address);
    }


    @Transactional
    void createTestFamilyHouse() {
        FamilyHouse familyHouse = new FamilyHouse();
        familyHouse.setAddress(addressService.getById(100L));
        familyHouse.setHouseMaterial(houseMaterialService.getById(100L));
        familyHouse.setHousingType(HousingTypeEnum.SECONDARY);
        familyHouse.setSwimmingPool(true);
        familyHouse.setNumberOfFloors((short) 12);
        familyHouse.setBuildingYear((short) 2000);

        familyHouseService.add(familyHouse);
    }

    @Test
    void givenFamilyHouse_whenPostRequestInFamilyHouses_thenAddedItFamilyHouse() throws Exception {
        RequestFamilyHouseDto requestFamilyHouseDto = new RequestFamilyHouseDto();
        requestFamilyHouseDto.setAddressId(100L);
        requestFamilyHouseDto.setHouseMaterialId(100L);
        requestFamilyHouseDto.setHousingType(HousingTypeEnum.SECONDARY);
        requestFamilyHouseDto.setSwimmingPool(true);
        requestFamilyHouseDto.setBuildingYear((short) 2000);
        requestFamilyHouseDto.setNumberOfFloors((short) 12);

        mvc.perform(post(BASIC_URL + "/houses/family-houses")
                .contentType(APPLICATION_JSON)
                .content(om.writeValueAsString(requestFamilyHouseDto))
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    @Transactional
    void whenDeleteRequestByIdInFamilyHouses_thenDeleteIt() throws Exception {
        createTestFamilyHouse();

        mvc.perform(delete(BASIC_URL + "/houses/family-houses/100")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> familyHouseService.getById(100L));
    }

    @Test
    @Transactional
    void whenGetRequestByIdInFamilyHouses_thenGetIt() throws Exception {
        createTestFamilyHouse();

        mvc.perform(get(BASIC_URL + "/houses/family-houses/100")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    @Transactional
    void whenGetRequestInFamilyHouses_thenGetAll() throws Exception {
        createTestFamilyHouse();
        createTestFamilyHouse();

        mvc.perform(get(BASIC_URL + "/houses/family-houses")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    void givenChangedFamilyHouse_whenPatchRequestByIdInFamilyHouses_thenChangingFamilyHouseById() throws Exception {
        createTestFamilyHouse();

        UpdateRequestFamilyHouseDto updateRequestDto = new UpdateRequestFamilyHouseDto();
        updateRequestDto.setHousingType(HousingTypeEnum.NEW_CONSTRUCTION);
        updateRequestDto.setSwimmingPool(false);

        FamilyHouse familyHouse1 = familyHouseService.getById(100L);
        Long id = familyHouse1.getId();

        mvc.perform(patch(BASIC_URL + "/houses/family-houses/100")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(updateRequestDto))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        FamilyHouse familyHouse = familyHouseService.getById(100L);
        assertThat(familyHouse.getHousingType()).isEqualTo(HousingTypeEnum.NEW_CONSTRUCTION);
        assertThat(familyHouse.getSwimmingPool()).isFalse();
    }

}
