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
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableDto;
import ru.senla.realestatemarket.dto.timetable.RentTimetableWithoutAnnouncementIdDto;
import ru.senla.realestatemarket.dto.timetable.RequestRentTimetableWithUserIdOfTenantDto;
import ru.senla.realestatemarket.service.timetable.rent.IApartmentAnnouncementRentTimetableService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/announcements/housing/apartments")
public class ApartmentAnnouncementRentTimetableController {

    private final IApartmentAnnouncementRentTimetableService apartmentAnnouncementRentTimetableService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{apartmentAnnouncementId}/timetables/rent")
    public List<RentTimetableWithoutAnnouncementIdAndWithUserIdOfTenantDto> getAllByApartmentId(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementRentTimetableService.getAllByApartmentIdDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/{apartmentAnnouncementId}/timetables/rent/only-date-times")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllByApartmentIdOnlyDateTimes(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementRentTimetableService.getAllByApartmentIdOnlyDateTimesDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = ""
    )
    @GetMapping("/open/{apartmentAnnouncementId}/timetables/rent/only-date-times")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllWithOpenStatusByApartmentIdOnlyDateTimes(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementRentTimetableService.getAllWithOpenStatusByApartmentIdOnlyDateTimesDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/{apartmentAnnouncementId}/timetables/rent")
    public ResponseEntity<RestResponseDto> addByApartmentIdWithoutPay(
            @PathVariable Long apartmentAnnouncementId,
            @RequestBody @Valid RequestRentTimetableWithUserIdOfTenantDto requestRentTimetableWithUserIdOfTenantDto
    ) {
        apartmentAnnouncementRentTimetableService.addByApartmentAnnouncementIdWithoutPay(
                requestRentTimetableWithUserIdOfTenantDto, apartmentAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement rent timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/owners/current/{apartmentAnnouncementId}/timetables/rent")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentUserByApartmentId(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementRentTimetableService.getAllOfCurrentUserByApartmentAnnouncementIdDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
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
        return apartmentAnnouncementRentTimetableService.getAllOfCurrentTenantUserDto(
                rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @GetMapping("/{apartmentAnnouncementId}/timetables/rent/tenants/current")
    public List<RentTimetableWithoutAnnouncementIdDto> getAllOfCurrentTenantUserByApartmentAnnouncementId(
            @PathVariable Long apartmentAnnouncementId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return apartmentAnnouncementRentTimetableService.getAllOfCurrentTenantUserByApartmentAnnouncementIdDto(
                apartmentAnnouncementId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/{apartmentAnnouncementId}/timetables/rent/tenants/current")
    public ResponseEntity<RestResponseDto> addByApartmentIdWithPayFromCurrentTenantUser(
            @PathVariable Long apartmentAnnouncementId,
            @RequestBody @Valid RequestRentTimetableDto requestRentTimetableDto
    ) {
        apartmentAnnouncementRentTimetableService.addByApartmentAnnouncementIdAndPayFromCurrentTenantUser(
                requestRentTimetableDto, apartmentAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement rent timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized user")}
    )
    @PostMapping("/open/{apartmentAnnouncementId}/timetables/rent/tenants/current")
    public ResponseEntity<RestResponseDto> addByApartmentAnnouncementIdWithOpenStatusAndPayFromCurrentTenantUser(
            @PathVariable Long apartmentAnnouncementId,
            @RequestBody @Valid RequestRentTimetableDto requestRentTimetableDto
    ) {
        apartmentAnnouncementRentTimetableService
                .addByApartmentAnnouncementIdWithOpenStatusAndPayFromCurrentTenantUser(
                        requestRentTimetableDto, apartmentAnnouncementId);

        return new ResponseEntity<>(new RestResponseDto(
                "Apartment announcement rent timetable has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
