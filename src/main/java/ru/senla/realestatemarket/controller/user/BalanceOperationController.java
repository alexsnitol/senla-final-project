package ru.senla.realestatemarket.controller.user;

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
import ru.senla.realestatemarket.dto.user.BalanceOperationWithoutUserIdDto;
import ru.senla.realestatemarket.dto.user.RequestBalanceOperationDto;
import ru.senla.realestatemarket.service.user.IBalanceOperationService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BalanceOperationController {

    private final IBalanceOperationService balanceOperationService;


    @ApiOperation(
            value = "",
            authorizations = {@Authorization("ADMIN")}
    )
    @GetMapping("/users/{userId}/balance-operations")
    public List<BalanceOperationWithoutUserIdDto> getAllBalanceOperationsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return balanceOperationService.getAllDtoByUserId(userId, rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            notes = "Add balance operation such as replenishment or reduction by user id",
            authorizations = {@Authorization("ADMIN")}
    )
    @PostMapping("/users/{userId}/balance-operations")
    public ResponseEntity<BalanceOperationWithoutUserIdDto> addBalanceOperationByUserId(
            @PathVariable Long userId,
            @RequestBody @Valid RequestBalanceOperationDto requestBalanceOperationDto
    ) {
        BalanceOperationWithoutUserIdDto responseDto
                = balanceOperationService.addByUserIdAndApplyOperation(requestBalanceOperationDto, userId);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "",
            authorizations = {@Authorization("Authorized users")}
    )
    @GetMapping("/users/current/balance-operations")
    public List<BalanceOperationWithoutUserIdDto> getAllBalanceOperationsByUserId(
            @RequestParam(required = false) String rsqlQuery,
            @RequestParam(required = false) String sortQuery
    ) {
        return balanceOperationService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

}
