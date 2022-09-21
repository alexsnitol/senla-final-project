package ru.senla.realestatemarket.controller.property;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/renovation-types")
public class RenovationTypeController {

    private final IRenovationTypeService renovationTypeService;


    @GetMapping("/{id}")
    public RenovationType getById(
            @PathVariable Long id
    ) {
        return renovationTypeService.getById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        renovationTypeService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Renovation type has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid RequestRenovationTypeDto requestRenovationTypeDto
    ) {
        renovationTypeService.updateById(requestRenovationTypeDto, id);

        return ResponseEntity.ok(new RestResponseDto("Renovation type has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<RenovationType> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return renovationTypeService.getAll(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestRenovationTypeDto requestRenovationTypeDto
    ) {
        renovationTypeService.add(requestRenovationTypeDto);

        return new ResponseEntity<>(new RestResponseDto("Renovation type has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }


}
