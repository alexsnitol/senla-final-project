package ru.senla.realestatemarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.senla.realestatemarket.mapper.address.AddressMapper;
import ru.senla.realestatemarket.mapper.address.CityMapper;
import ru.senla.realestatemarket.mapper.address.RegionMapper;
import ru.senla.realestatemarket.mapper.address.StreetMapper;
import ru.senla.realestatemarket.mapper.announcement.AnnouncementMapper;
import ru.senla.realestatemarket.mapper.announcement.ApartmentAnnouncementMapper;
import ru.senla.realestatemarket.mapper.announcement.FamilyHouseAnnouncementMapper;
import ru.senla.realestatemarket.mapper.announcement.HousingAnnouncementMapper;
import ru.senla.realestatemarket.mapper.announcement.LandAnnouncementMapper;
import ru.senla.realestatemarket.mapper.dictionaty.AnnouncementTopPriceMapper;
import ru.senla.realestatemarket.mapper.house.ApartmentHouseMapper;
import ru.senla.realestatemarket.mapper.house.FamilyHouseMapper;
import ru.senla.realestatemarket.mapper.house.HouseMapper;
import ru.senla.realestatemarket.mapper.house.HouseMaterialMapper;
import ru.senla.realestatemarket.mapper.property.ApartmentPropertyMapper;
import ru.senla.realestatemarket.mapper.property.FamilyHousePropertyMapper;
import ru.senla.realestatemarket.mapper.property.HousingPropertyMapper;
import ru.senla.realestatemarket.mapper.property.LandPropertyMapper;
import ru.senla.realestatemarket.mapper.property.PropertyMapper;
import ru.senla.realestatemarket.mapper.property.RenovationTypeMapper;
import ru.senla.realestatemarket.mapper.timetable.rent.ApartmentAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.mapper.timetable.rent.FamilyHouseAnnouncementRentTimetableMapper;
import ru.senla.realestatemarket.mapper.timetable.top.ApartmentAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.mapper.timetable.top.FamilyHouseAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.mapper.timetable.top.LandAnnouncementTopTimetableMapper;
import ru.senla.realestatemarket.mapper.user.BalanceOperationMapper;
import ru.senla.realestatemarket.mapper.user.MessageMapper;
import ru.senla.realestatemarket.mapper.user.ReviewMapper;
import ru.senla.realestatemarket.mapper.user.RoleMapper;
import ru.senla.realestatemarket.mapper.user.UserMapper;
import ru.senla.realestatemarket.repo.address.IAddressRepository;
import ru.senla.realestatemarket.repo.address.ICityRepository;
import ru.senla.realestatemarket.repo.address.IRegionRepository;
import ru.senla.realestatemarket.repo.address.IStreetRepository;
import ru.senla.realestatemarket.repo.announcement.IAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.IApartmentAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.IFamilyHouseAnnouncementRepository;
import ru.senla.realestatemarket.repo.announcement.ILandAnnouncementRepository;
import ru.senla.realestatemarket.repo.dictionary.IAnnouncementTopPriceRepository;
import ru.senla.realestatemarket.repo.house.IApartmentHouseRepository;
import ru.senla.realestatemarket.repo.house.IFamilyHouseRepository;
import ru.senla.realestatemarket.repo.house.IHouseMaterialRepository;
import ru.senla.realestatemarket.repo.house.IHouseRepository;
import ru.senla.realestatemarket.repo.property.IApartmentPropertyRepository;
import ru.senla.realestatemarket.repo.property.IFamilyHousePropertyRepository;
import ru.senla.realestatemarket.repo.property.IHousingPropertyRepository;
import ru.senla.realestatemarket.repo.property.ILandPropertyRepository;
import ru.senla.realestatemarket.repo.property.IPropertyRepository;
import ru.senla.realestatemarket.repo.property.IRenovationTypeRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IApartmentAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.purchase.rent.IFamilyHouseAnnouncementRentPurchaseRepository;
import ru.senla.realestatemarket.repo.purchase.top.IApartmentAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.purchase.top.IFamilyHouseAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.purchase.top.ILandAnnouncementTopPurchaseRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IApartmentAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.timetable.rent.IFamilyHouseAnnouncementRentTimetableRepository;
import ru.senla.realestatemarket.repo.timetable.top.IApartmentAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.timetable.top.IFamilyHouseAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.timetable.top.ILandAnnouncementTopTimetableRepository;
import ru.senla.realestatemarket.repo.user.IAuthorityRepository;
import ru.senla.realestatemarket.repo.user.IBalanceOperationRepository;
import ru.senla.realestatemarket.repo.user.IMessageRepository;
import ru.senla.realestatemarket.repo.user.IReviewRepository;
import ru.senla.realestatemarket.repo.user.IRoleRepository;
import ru.senla.realestatemarket.repo.user.IUserRepository;
import ru.senla.realestatemarket.service.address.IAddressService;
import ru.senla.realestatemarket.service.address.ICityService;
import ru.senla.realestatemarket.service.address.IRegionService;
import ru.senla.realestatemarket.service.address.IStreetService;
import ru.senla.realestatemarket.service.address.impl.AddressServiceImpl;
import ru.senla.realestatemarket.service.address.impl.CityServiceImpl;
import ru.senla.realestatemarket.service.address.impl.RegionServiceImpl;
import ru.senla.realestatemarket.service.address.impl.StreetServiceImpl;
import ru.senla.realestatemarket.service.announcement.IAnnouncementService;
import ru.senla.realestatemarket.service.announcement.IApartmentAnnouncementService;
import ru.senla.realestatemarket.service.announcement.IFamilyHouseAnnouncementService;
import ru.senla.realestatemarket.service.announcement.ILandAnnouncementService;
import ru.senla.realestatemarket.service.announcement.impl.AnnouncementServiceImpl;
import ru.senla.realestatemarket.service.announcement.impl.ApartmentAnnouncementServiceImpl;
import ru.senla.realestatemarket.service.announcement.impl.FamilyHouseAnnouncementServiceImpl;
import ru.senla.realestatemarket.service.announcement.impl.LandAnnouncementServiceImpl;
import ru.senla.realestatemarket.service.dictionary.IAnnouncementTopPriceService;
import ru.senla.realestatemarket.service.dictionary.impl.AnnouncementTopPriceServiceImpl;
import ru.senla.realestatemarket.service.house.IApartmentHouseService;
import ru.senla.realestatemarket.service.house.IFamilyHouseService;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;
import ru.senla.realestatemarket.service.house.IHouseService;
import ru.senla.realestatemarket.service.house.impl.ApartmentHouseServiceImpl;
import ru.senla.realestatemarket.service.house.impl.FamilyHouseServiceImpl;
import ru.senla.realestatemarket.service.house.impl.HouseMaterialServiceImpl;
import ru.senla.realestatemarket.service.house.impl.HouseServiceImpl;
import ru.senla.realestatemarket.service.property.IApartmentPropertyService;
import ru.senla.realestatemarket.service.property.IFamilyHousePropertyService;
import ru.senla.realestatemarket.service.property.IHousingPropertyService;
import ru.senla.realestatemarket.service.property.ILandPropertyService;
import ru.senla.realestatemarket.service.property.IPropertyService;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;
import ru.senla.realestatemarket.service.property.impl.ApartmentPropertyServiceImpl;
import ru.senla.realestatemarket.service.property.impl.FamilyHousePropertyServiceImpl;
import ru.senla.realestatemarket.service.property.impl.HousingPropertyServiceImpl;
import ru.senla.realestatemarket.service.property.impl.LandPropertyServiceImpl;
import ru.senla.realestatemarket.service.property.impl.PropertyServiceImpl;
import ru.senla.realestatemarket.service.property.impl.RenovationTypeServiceImpl;
import ru.senla.realestatemarket.service.purchase.rent.IApartmentAnnouncementRentPurchaseService;
import ru.senla.realestatemarket.service.purchase.rent.IFamilyHouseAnnouncementRentPurchaseService;
import ru.senla.realestatemarket.service.purchase.rent.impl.ApartmentAnnouncementRentPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.rent.impl.FamilyHouseAnnouncementRentPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.top.IApartmentAnnouncementTopPurchaseService;
import ru.senla.realestatemarket.service.purchase.top.IFamilyHouseAnnouncementTopPurchaseService;
import ru.senla.realestatemarket.service.purchase.top.ILandAnnouncementTopPurchaseService;
import ru.senla.realestatemarket.service.purchase.top.impl.ApartmentAnnouncementTopPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.top.impl.FamilyHouseAnnouncementTopPurchaseServiceImpl;
import ru.senla.realestatemarket.service.purchase.top.impl.LandAnnouncementTopPurchaseServiceImpl;
import ru.senla.realestatemarket.service.timetable.rent.IApartmentAnnouncementRentTimetableService;
import ru.senla.realestatemarket.service.timetable.rent.IFamilyHouseAnnouncementRentTimetableService;
import ru.senla.realestatemarket.service.timetable.rent.impl.ApartmentAnnouncementRentTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.rent.impl.FamilyHouseAnnouncementRentTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.IApartmentAnnouncementTopTimetableService;
import ru.senla.realestatemarket.service.timetable.top.IFamilyHouseAnnouncementTopTimetableService;
import ru.senla.realestatemarket.service.timetable.top.ILandAnnouncementTopTimetableService;
import ru.senla.realestatemarket.service.timetable.top.impl.ApartmentAnnouncementTopTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.impl.FamilyHouseAnnouncementTopTimetableServiceImpl;
import ru.senla.realestatemarket.service.timetable.top.impl.LandAnnouncementTopTimetableServiceImpl;
import ru.senla.realestatemarket.service.user.IAuthService;
import ru.senla.realestatemarket.service.user.IAuthorityService;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;
import ru.senla.realestatemarket.service.user.IMessageService;
import ru.senla.realestatemarket.service.user.IReviewService;
import ru.senla.realestatemarket.service.user.IUserService;
import ru.senla.realestatemarket.service.user.impl.AuthServiceImpl;
import ru.senla.realestatemarket.service.user.impl.AuthorityServiceImpl;
import ru.senla.realestatemarket.service.user.impl.BalanceOperationServiceImpl;
import ru.senla.realestatemarket.service.user.impl.MessageServiceImpl;
import ru.senla.realestatemarket.service.user.impl.ReviewServiceImpl;
import ru.senla.realestatemarket.service.user.impl.UserServiceImpl;
import ru.senla.realestatemarket.util.JwtTokenUtil;
import ru.senla.realestatemarket.util.UserUtil;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    public static final String TEST_SECURE_KEY = "examplesupersecurekeyforencodinganddecodingtheHS256algorithm";


    // MOCKED REPOSITORIES

    @Bean
    public IUserRepository mockedUserRepository() {
        return mock(IUserRepository.class);
    }
    
    @Bean
    public IAddressRepository mockedAddressRepository() {
        return mock(IAddressRepository.class);
    }
    
    @Bean
    public ICityRepository mockedCityRepository() {
        return mock(ICityRepository.class);
    }
    
    @Bean
    public IRegionRepository mockedRegionRepository() {
        return mock(IRegionRepository.class);
    }
    
    @Bean
    public IStreetRepository mockedStreetRepository() {
        return mock(IStreetRepository.class);
    }
    
    @Bean
    public IAnnouncementRepository mockedAnnouncementRepository() {
        return mock(IAnnouncementRepository.class);
    }
    
    @Bean
    public IApartmentAnnouncementRepository mockedApartmentAnnouncementRepository() {
        return mock(IApartmentAnnouncementRepository.class);
    }
    
    @Bean
    public IFamilyHouseAnnouncementRepository mockedFamilyHouseAnnouncementRepository() {
        return mock(IFamilyHouseAnnouncementRepository.class);
    }
    
    @Bean
    public ILandAnnouncementRepository mockedLandAnnouncementRepository() {
        return mock(ILandAnnouncementRepository.class);
    }
    
    @Bean
    public IAnnouncementTopPriceRepository mockedAnnouncementTopPriceRepository() {
        return mock(IAnnouncementTopPriceRepository.class);
    }
    
    @Bean
    public IApartmentHouseRepository mockedApartmentHouseRepository() {
        return mock(IApartmentHouseRepository.class);
    }
    
    @Bean
    public IFamilyHouseRepository mockedFamilyHouseRepository() {
        return mock(IFamilyHouseRepository.class);
    }
    
    @Bean
    public IHouseMaterialRepository mockedHouseMaterialRepository() {
        return mock(IHouseMaterialRepository.class);
    }
    
    @Bean
    public IHouseRepository mockedHouseRepository() {
        return mock(IHouseRepository.class);
    }
    
    @Bean
    public IApartmentPropertyRepository mockedApartmentPropertyRepository() {
        return mock(IApartmentPropertyRepository.class);
    }
    
    @Bean
    public IFamilyHousePropertyRepository mockedFamilyHousePropertyRepository() {
        return mock(IFamilyHousePropertyRepository.class);
    }
    
    @Bean
    public ILandPropertyRepository mockedLandPropertyRepository() {
        return mock(ILandPropertyRepository.class);
    }
    
    @Bean
    public IHousingPropertyRepository mockedHousingPropertyRepository() {
        return mock(IHousingPropertyRepository.class);
    }
    
    @Bean
    public IPropertyRepository mockedPropertyRepository() {
        return mock(IPropertyRepository.class);
    }
    
    @Bean
    public IRenovationTypeRepository mockedRenovationTypeRepository() {
        return mock(IRenovationTypeRepository.class);
    }

    @Bean
    public IApartmentAnnouncementRentPurchaseRepository mockedApartmentAnnouncementRentPurchaseRepository() {
        return mock(IApartmentAnnouncementRentPurchaseRepository.class);
    }

    @Bean
    public IFamilyHouseAnnouncementRentPurchaseRepository mockedFamilyHouseAnnouncementRentPurchaseRepository() {
        return mock(IFamilyHouseAnnouncementRentPurchaseRepository.class);
    }
    
    @Bean
    public IApartmentAnnouncementTopPurchaseRepository mockedApartmentAnnouncementTopPurchaseRepository() {
        return mock(IApartmentAnnouncementTopPurchaseRepository.class);
    }

    @Bean
    public IFamilyHouseAnnouncementTopPurchaseRepository mockedFamilyHouseAnnouncementTopPurchaseRepository() {
        return mock(IFamilyHouseAnnouncementTopPurchaseRepository.class);
    }

    @Bean
    public ILandAnnouncementTopPurchaseRepository mockedLandAnnouncementTopPurchaseRepository() {
        return mock(ILandAnnouncementTopPurchaseRepository.class);
    }


    @Bean
    public IApartmentAnnouncementRentTimetableRepository mockedApartmentAnnouncementRentTimetableRepository() {
        return mock(IApartmentAnnouncementRentTimetableRepository.class);
    }

    @Bean
    public IFamilyHouseAnnouncementRentTimetableRepository mockedFamilyHouseAnnouncementRentTimetableRepository() {
        return mock(IFamilyHouseAnnouncementRentTimetableRepository.class);
    }

    @Bean
    public IApartmentAnnouncementTopTimetableRepository mockedApartmentAnnouncementTopTimetableRepository() {
        return mock(IApartmentAnnouncementTopTimetableRepository.class);
    }

    @Bean
    public IFamilyHouseAnnouncementTopTimetableRepository mockedFamilyHouseAnnouncementTopTimetableRepository() {
        return mock(IFamilyHouseAnnouncementTopTimetableRepository.class);
    }

    @Bean
    public ILandAnnouncementTopTimetableRepository mockedLandAnnouncementTopTimetableRepository() {
        return mock(ILandAnnouncementTopTimetableRepository.class);
    }
    
    @Bean
    public IAuthorityRepository mockedAuthorityRepository() {
        return mock(IAuthorityRepository.class);
    }
    
    @Bean
    public IBalanceOperationRepository mockedBalanceOperationRepository() {
        return mock(IBalanceOperationRepository.class);
    }
    
    @Bean
    public IMessageRepository mockedMessageRepository() {
        return mock(IMessageRepository.class);
    }
    
    @Bean
    public IReviewRepository mockedReviewRepository() {
        return mock(IReviewRepository.class);
    }

    @Bean
    public IRoleRepository mockedRoleRepository() {
        return mock(IRoleRepository.class);
    }

    // END MOCKED REPOSITORIES

    
    // SERVICES

    @Bean
    public IAddressService addressService() {
        return new AddressServiceImpl(mockedAddressRepository(), mockedStreetRepository(), mockedAddressMapper());
    }

    @Bean
    public ICityService cityService() {
        return new CityServiceImpl(mockedCityRepository(), mockedRegionRepository(), mockedCityMapper());
    }

    @Bean
    public IRegionService regionService() {
        return new RegionServiceImpl(mockedRegionRepository(), mockedRegionMapper());
    }

    @Bean
    public IStreetService streetService() {
        return new StreetServiceImpl(mockedStreetRepository(), mockedCityRepository(), mockedStreetMapper());
    }

    @Bean
    public IAnnouncementService announcementService() {
        return new AnnouncementServiceImpl(mockedAnnouncementRepository(), mockedApartmentAnnouncementRepository(),
                mockedFamilyHouseAnnouncementRepository(), mockedLandAnnouncementRepository(),
                mockedUserUtil(), mockedAnnouncementMapper());
    }

    @Bean
    public IApartmentAnnouncementService apartmentAnnouncementService() {
        return new ApartmentAnnouncementServiceImpl(
                mockedApartmentAnnouncementRepository(), mockedApartmentPropertyRepository(), mockedUserUtil(),
                mockedApartmentAnnouncementMapper());
    }

    @Bean
    public IFamilyHouseAnnouncementService familyHouseAnnouncementService() {
        return new FamilyHouseAnnouncementServiceImpl(
                mockedFamilyHouseAnnouncementRepository(), mockedFamilyHousePropertyRepository(), mockedUserUtil(),
                mockedFamilyHouseAnnouncementMapper());
    }

    @Bean
    public ILandAnnouncementService landAnnouncementService() {
        return new LandAnnouncementServiceImpl(
                mockedLandAnnouncementRepository(), mockedLandPropertyRepository(), mockedUserUtil(),
                mockedLandAnnouncementMapper());
    }

    @Bean
    public IAnnouncementTopPriceService announcementTopPriceService() {
        return new AnnouncementTopPriceServiceImpl(mockedAnnouncementTopPriceRepository(),
                mockedAnnouncementTopPriceMapper());
    }

    @Bean
    public IApartmentHouseService apartmentHouseService() {
        return new ApartmentHouseServiceImpl(
                mockedApartmentHouseRepository(), mockedHouseMaterialRepository(), mockedAddressRepository(),
                mockedApartmentHouseMapper());
    }

    @Bean
    public IFamilyHouseService familyHouseService() {
        return new FamilyHouseServiceImpl(
                mockedFamilyHouseRepository(), mockedHouseMaterialRepository(), mockedAddressRepository(),
                mockedFamilyHouseMapper());
    }

    @Bean
    public IHouseMaterialService houseMaterialService() {
        return new HouseMaterialServiceImpl(mockedHouseMaterialRepository(), mockedHouseMaterialMapper());
    }

    @Bean
    public IHouseService houseService() {
        return new HouseServiceImpl(
                mockedHouseMaterialRepository(), mockedAddressRepository(), mockedHouseRepository(),
                mockedHouseMapper());
    }

    @Bean
    public IApartmentPropertyService apartmentPropertyService() {
        return new ApartmentPropertyServiceImpl(
                mockedRenovationTypeRepository(), mockedApartmentPropertyRepository(),
                mockedApartmentHouseRepository(), mockedUserRepository(), mockedUserUtil(),
                mockedApartmentPropertyMapper());
    }

    @Bean
    public IFamilyHousePropertyService familyHousePropertyService() {
        return new FamilyHousePropertyServiceImpl(
                mockedRenovationTypeRepository(), mockedUserRepository(),
                mockedFamilyHousePropertyRepository(), mockedFamilyHouseRepository(), mockedUserUtil(),
                mockedFamilyHousePropertyMapper());
    }

    @Bean
    public ILandPropertyService landPropertyService() {
        return new LandPropertyServiceImpl(
                mockedUserRepository(), mockedLandPropertyRepository(), mockedStreetRepository(), mockedUserUtil(),
                mockedLandPropertyMapper());
    }

    @Bean
    public IHousingPropertyService housingPropertyService() {
        return new HousingPropertyServiceImpl(mockedHousingPropertyRepository(), mockedUserUtil(),
                mockedHousingPropertyMapper());
    }

    @Bean
    public IPropertyService propertyService() {
        return new PropertyServiceImpl(mockedPropertyRepository(), mockedUserUtil(), mockedPropertyMapper());
    }

    @Bean
    public IRenovationTypeService renovationTypeService() {
        return new RenovationTypeServiceImpl(mockedRenovationTypeRepository(), mockedRenovationTypeMapper());
    }

    @Bean
    public IApartmentAnnouncementRentPurchaseService apartmentAnnouncementRentPurchaseService() {
        return new ApartmentAnnouncementRentPurchaseServiceImpl(mockedApartmentAnnouncementRentPurchaseRepository());
    }

    @Bean
    public IFamilyHouseAnnouncementRentPurchaseService familyHouseAnnouncementRentPurchaseService() {
        return new FamilyHouseAnnouncementRentPurchaseServiceImpl(
                mockedFamilyHouseAnnouncementRentPurchaseRepository());
    }

    @Bean
    public IApartmentAnnouncementTopPurchaseService apartmentAnnouncementTopPurchaseService() {
        return new ApartmentAnnouncementTopPurchaseServiceImpl(mockedApartmentAnnouncementTopPurchaseRepository());
    }

    @Bean
    public IFamilyHouseAnnouncementTopPurchaseService familyHouseAnnouncementTopPurchaseService() {
        return new FamilyHouseAnnouncementTopPurchaseServiceImpl(mockedFamilyHouseAnnouncementTopPurchaseRepository());
    }

    @Bean
    public ILandAnnouncementTopPurchaseService landAnnouncementTopPurchaseService() {
        return new LandAnnouncementTopPurchaseServiceImpl(mockedLandAnnouncementTopPurchaseRepository());
    }

    @Bean
    public IApartmentAnnouncementRentTimetableService apartmentAnnouncementRentTimetableService() {
        return new ApartmentAnnouncementRentTimetableServiceImpl(
                mockedUserRepository(), mockedUserUtil(), mockedBalanceOperationService(),
                mockedApartmentAnnouncementRentTimetableRepository(), mockedApartmentAnnouncementRepository(),
                mockedApartmentAnnouncementRentPurchaseRepository(), mockedApartmentAnnouncementRentTimetableMapper());
    }

    @Bean
    public IFamilyHouseAnnouncementRentTimetableService familyHouseAnnouncementRentTimetableService() {
        return new FamilyHouseAnnouncementRentTimetableServiceImpl(
                mockedUserRepository(), mockedUserUtil(), mockedBalanceOperationService(),
                mockedFamilyHouseAnnouncementRentTimetableRepository(), mockedFamilyHouseAnnouncementRepository(),
                mockedFamilyHouseAnnouncementRentPurchaseRepository(),
                mockedFamilyHouseAnnouncementRentTimetableMapper());
    }

    @Bean
    public IApartmentAnnouncementTopTimetableService apartmentAnnouncementTopTimetableService() {
        return new ApartmentAnnouncementTopTimetableServiceImpl(
                mockedUserRepository(), mockedUserUtil(), mockedBalanceOperationService(),
                mockedApartmentAnnouncementTopTimetableRepository(), mockedApartmentAnnouncementRepository(),
                mockedApartmentAnnouncementTopPurchaseRepository(), mockedAnnouncementTopPriceRepository(),
                mockedApartmentAnnouncementTopTimetableMapper());
    }

    @Bean
    public IFamilyHouseAnnouncementTopTimetableService familyHouseAnnouncementTopTimetableService() {
        return new FamilyHouseAnnouncementTopTimetableServiceImpl(
                mockedUserRepository(), mockedUserUtil(), mockedBalanceOperationService(),
                mockedFamilyHouseAnnouncementTopTimetableRepository(), mockedFamilyHouseAnnouncementRepository(),
                mockedFamilyHouseAnnouncementTopPurchaseRepository(), mockedAnnouncementTopPriceRepository(),
                mockedFamilyHouseAnnouncementTopTimetableMapper());
    }

    @Bean
    public ILandAnnouncementTopTimetableService landAnnouncementTopTimetableService() {
        return new LandAnnouncementTopTimetableServiceImpl(
                mockedUserRepository(), mockedUserUtil(), mockedBalanceOperationService(),
                mockedLandAnnouncementTopTimetableRepository(), mockedLandAnnouncementRepository(),
                mockedLandAnnouncementTopPurchaseRepository(), mockedAnnouncementTopPriceRepository(),
                mockedLandAnnouncementTopTimetableMapper());
    }

    @Bean
    public IAuthorityService authorityService() {
        return new AuthorityServiceImpl(mockedAuthorityRepository());
    }

    @Bean
    public IBalanceOperationService balanceOperationService() {
        return new BalanceOperationServiceImpl(
                mockedBalanceOperationRepository(), mockedUserRepository(), mockedUserUtil(),
                mockedBalanceOperationMapper());
    }
    
    @Bean
    public IBalanceOperationService mockedBalanceOperationService() {
        return mock(IBalanceOperationService.class);
    }

    @Bean
    public IMessageService messageService() {
        return new MessageServiceImpl(mockedMessageRepository(), mockedUserRepository(), mockedUserUtil(),
                mockedMessageMapper());
    }

    @Bean
    public IReviewService reviewService() {
        return new ReviewServiceImpl(mockedReviewRepository(), mockedUserRepository(), mockedUserUtil(),
                mockedReviewMapper());
    }

    @Bean
    public IAuthService authService() {
        return new AuthServiceImpl(mockedUserService(), jwtTokenUtil(), mockedAuthenticationManager());
    }

    @Bean
    public IUserService userService() {
        return new UserServiceImpl(
                mockedUserRepository(), mockedRoleRepository(), mockedUserUtil(), mockedPasswordEncoder(),
                mockedUserMapper()
        );
    }

    @Bean
    public IUserService mockedUserService() {
        return mock(UserServiceImpl.class);
    }

    // END SERVICES


    // MAPPERS

    @Bean
    public UserMapper mockedUserMapper() {
        return mock(UserMapper.class);
    }
    
    @Bean
    public AddressMapper mockedAddressMapper() {
        return mock(AddressMapper.class);
    }
    
    @Bean
    public CityMapper mockedCityMapper() {
        return mock(CityMapper.class);
    }
    
    @Bean
    public RegionMapper mockedRegionMapper() {
        return mock(RegionMapper.class);
    }
    
    @Bean
    public StreetMapper mockedStreetMapper() {
        return mock(StreetMapper.class);
    }
    
    @Bean
    public AnnouncementMapper mockedAnnouncementMapper() {
        return mock(AnnouncementMapper.class);
    }

    @Bean
    public HousingAnnouncementMapper mockedHousingAnnouncementMapper() {
        return mock(HousingAnnouncementMapper.class);
    }

    @Bean
    public ApartmentAnnouncementMapper mockedApartmentAnnouncementMapper() {
        return mock(ApartmentAnnouncementMapper.class);
    }
    
    @Bean
    public FamilyHouseAnnouncementMapper mockedFamilyHouseAnnouncementMapper() {
        return mock(FamilyHouseAnnouncementMapper.class);
    }
    
    @Bean
    public LandAnnouncementMapper mockedLandAnnouncementMapper() {
        return mock(LandAnnouncementMapper.class);
    }
    
    @Bean
    public AnnouncementTopPriceMapper mockedAnnouncementTopPriceMapper() {
        return mock(AnnouncementTopPriceMapper.class);
    }
    
    @Bean
    public ApartmentHouseMapper mockedApartmentHouseMapper() {
        return mock(ApartmentHouseMapper.class);
    }
    
    @Bean
    public FamilyHouseMapper mockedFamilyHouseMapper() {
        return mock(FamilyHouseMapper.class);
    }
    
    @Bean
    public HouseMaterialMapper mockedHouseMaterialMapper() {
        return mock(HouseMaterialMapper.class);
    }
    
    @Bean
    public HouseMapper mockedHouseMapper() {
        return mock(HouseMapper.class);
    }
    
    @Bean
    public ApartmentPropertyMapper mockedApartmentPropertyMapper() {
        return mock(ApartmentPropertyMapper.class);
    }
    
    @Bean
    public FamilyHousePropertyMapper mockedFamilyHousePropertyMapper() {
        return mock(FamilyHousePropertyMapper.class);
    }
    
    @Bean
    public LandPropertyMapper mockedLandPropertyMapper() {
        return mock(LandPropertyMapper.class);
    }
    
    @Bean
    public HousingPropertyMapper mockedHousingPropertyMapper() {
        return mock(HousingPropertyMapper.class);
    }
    
    @Bean
    public PropertyMapper mockedPropertyMapper() {
        return mock(PropertyMapper.class);
    }
    
    @Bean
    public RenovationTypeMapper mockedRenovationTypeMapper() {
        return mock(RenovationTypeMapper.class);
    }

    @Bean
    public ApartmentAnnouncementRentTimetableMapper mockedApartmentAnnouncementRentTimetableMapper() {
        return mock(ApartmentAnnouncementRentTimetableMapper.class);
    }

    @Bean
    public FamilyHouseAnnouncementRentTimetableMapper mockedFamilyHouseAnnouncementRentTimetableMapper() {
        return mock(FamilyHouseAnnouncementRentTimetableMapper.class);
    }

    @Bean
    public ApartmentAnnouncementTopTimetableMapper mockedApartmentAnnouncementTopTimetableMapper() {
        return mock(ApartmentAnnouncementTopTimetableMapper.class);
    }

    @Bean
    public FamilyHouseAnnouncementTopTimetableMapper mockedFamilyHouseAnnouncementTopTimetableMapper() {
        return mock(FamilyHouseAnnouncementTopTimetableMapper.class);
    }

    @Bean
    public LandAnnouncementTopTimetableMapper mockedLandAnnouncementTopTimetableMapper() {
        return mock(LandAnnouncementTopTimetableMapper.class);
    }
    
    @Bean
    public BalanceOperationMapper mockedBalanceOperationMapper() {
        return mock(BalanceOperationMapper.class);
    }
    
    @Bean
    public MessageMapper mockedMessageMapper() {
        return mock(MessageMapper.class);
    }
    
    @Bean
    public ReviewMapper mockedReviewMapper() {
        return mock(ReviewMapper.class);
    }

    @Bean
    public RoleMapper mockedRoleMapper() {
        return mock(RoleMapper.class);
    }

    // END MAPPERS

    @Bean
    public AuthenticationManager mockedAuthenticationManager() {
        return mock(AuthenticationManager.class);
    }
    
    @Bean
    public PasswordEncoder mockedPasswordEncoder() {
        return mock(PasswordEncoder.class);
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(TEST_SECURE_KEY);
    }

    @Bean
    public UserUtil mockedUserUtil() {
        return mock(UserUtil.class);
    }

}
