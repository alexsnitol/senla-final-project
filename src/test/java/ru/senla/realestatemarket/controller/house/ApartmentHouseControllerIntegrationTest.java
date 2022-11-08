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
import ru.senla.realestatemarket.dto.house.RequestApartmentHouseDto;
import ru.senla.realestatemarket.model.address.Address;
import ru.senla.realestatemarket.model.address.City;
import ru.senla.realestatemarket.model.address.Region;
import ru.senla.realestatemarket.model.address.Street;
import ru.senla.realestatemarket.model.house.ApartmentHouse;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.model.house.HousingTypeEnum;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.address.ICityService;
import ru.senla.realestatemarket.service.address.IRegionService;
import ru.senla.realestatemarket.service.address.IStreetService;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;
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
class ApartmentHouseControllerIntegrationTest extends AbstractTestcontainersIntegrationTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    IApartmentHouseService apartmentHouseService;
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
    void createTestApartmentHouse() {
        ApartmentHouse apartmentHouse = new ApartmentHouse();
        apartmentHouse.setAddress(addressService.getById(100L));
        apartmentHouse.setHouseMaterial(houseMaterialService.getById(100L));
        apartmentHouse.setHousingType(HousingTypeEnum.SECONDARY);
        apartmentHouse.setElevator(true);
        apartmentHouse.setNumberOfFloors((short) 12);
        apartmentHouse.setBuildingYear((short) 2000);

        apartmentHouseService.add(apartmentHouse);
    }

    @Test
    void givenApartmentHouse_whenPostRequestInApartmentHouses_thenAddedItApartmentHouse() throws Exception {
        RequestApartmentHouseDto requestApartmentHouseDto = new RequestApartmentHouseDto();
        requestApartmentHouseDto.setAddressId(100L);
        requestApartmentHouseDto.setHouseMaterialId(100L);
        requestApartmentHouseDto.setHousingType(HousingTypeEnum.SECONDARY);
        requestApartmentHouseDto.setElevator(true);
        requestApartmentHouseDto.setBuildingYear((short) 2000);
        requestApartmentHouseDto.setNumberOfFloors((short) 12);

        mvc.perform(post(BASIC_URL + "/houses/apartment-houses")
                .contentType(APPLICATION_JSON)
                .content(om.writeValueAsString(requestApartmentHouseDto))
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    @Transactional
    void whenDeleteRequestByIdInApartmentHouses_thenDeleteIt() throws Exception {
        createTestApartmentHouse();

        mvc.perform(delete(BASIC_URL + "/houses/apartment-houses/100")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(EntityNotFoundException.class, () -> apartmentHouseService.getById(100L));
    }

    @Test
    @Transactional
    void whenGetRequestByIdInApartmentHouses_thenGetIt() throws Exception {
        createTestApartmentHouse();

        mvc.perform(get(BASIC_URL + "/houses/apartment-houses/100")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    @Transactional
    void whenGetRequestInApartmentHouses_thenGetAll() throws Exception {
        createTestApartmentHouse();
        createTestApartmentHouse();

        mvc.perform(get(BASIC_URL + "/houses/apartment-houses")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    void givenChangedApartmentHouse_whenPatchRequestByIdInApartmentHouses_thenChangingApartmentHouseById() throws Exception {
        createTestApartmentHouse();

        RequestApartmentHouseDto requestApartmentHouseDto = new RequestApartmentHouseDto();
        requestApartmentHouseDto.setHousingType(HousingTypeEnum.NEW_CONSTRUCTION);
        requestApartmentHouseDto.setElevator(false);

        mvc.perform(patch(BASIC_URL + "/houses/apartment-houses/100")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(requestApartmentHouseDto))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        ApartmentHouse apartmentHouse = apartmentHouseService.getById(100L);
        assertThat(apartmentHouse.getHousingType()).isEqualTo(HousingTypeEnum.NEW_CONSTRUCTION);
        assertThat(apartmentHouse.getElevator()).isFalse();
    }

}
