package ru.senla.realestatemarket.controller.timetable.rent;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.service.timetable.rent.IFamilyHouseAnnouncementRentTimetableService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/family-houses")
public class FamilyHouseAnnouncementRentTimetableController {

    private final IFamilyHouseAnnouncementRentTimetableService familyHouseAnnouncementRentTimetableService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{familyHouseAnnouncementId}/timetables/rent")
    public List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByFamilyHouseId(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementRentTimetableService.getAllByFamilyHouseIdDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = ""
    )
    @GetMapping("/{familyHouseAnnouncementId}/timetables/rent/only-date-times")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllByFamilyHouseIdOnlyDateTimes(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementRentTimetableService.getAllByFamilyHouseIdForUsersDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/{familyHouseAnnouncementId}/timetables/rent")
    public ResponseEntity<RestResponseDto> addByFamilyHouseIdWithoutPay(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestBody @Valid RequestRentTimetableWithUserIdOfTenantDto requestRentTimetableWithUserIdOfTenantDto
    ) {
        familyHouseAnnouncementRentTimetableService.addByFamilyHouseAnnouncementIdWithoutPay(
                requestRentTimetableWithUserIdOfTenantDto, familyHouseAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Family house announcement rent timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current/{familyHouseAnnouncementId}/timetables/rent")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByFamilyHouseId(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementRentTimetableService.getAllOfCurrentUserByFamilyHouseAnnouncementIdDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/timetables/rent/tenants/current")
    public List<RentTimetableDto> getAllOfCurrentTenantUser(
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementRentTimetableService.getAllOfCurrentTenantUserDto(
                rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/{familyHouseAnnouncementId}/timetables/rent/tenants/current")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByFamilyHouseAnnouncementId(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return familyHouseAnnouncementRentTimetableService.getAllOfCurrentTenantUserByFamilyHouseAnnouncementIdDto(
                familyHouseAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/{familyHouseAnnouncementId}/timetables/rent/tenants/current")
    public ResponseEntity<RestResponseDto> addByFamilyHouseIdWithPayFromCurrentTenantUser(
            @PathVariable Long familyHouseAnnouncementId,
            @RequestBody @Valid RequestRentTimetableDto requestRentTimetableDto
    ) {
        familyHouseAnnouncementRentTimetableService.addByFamilyHouseAnnouncementIdWithPayFromCurrentTenantUser(
                requestRentTimetableDto, familyHouseAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "FamilyHouse announcement rent timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
