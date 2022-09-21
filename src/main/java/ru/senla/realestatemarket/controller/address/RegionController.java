package ru.senla.realestatemarket.controller.address;

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
import ru.senla.realestatemarket.dto.address.RegionDto;
import ru.senla.realestatemarket.dto.address.RequestRegionDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.address.IRegionService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final IRegionService regionService;


    @GetMapping("/{id}")
    public RegionDto getById(
            @PathVariable Long id
    ) {
        return regionService.getDtoById(id);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        regionService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Region has been deleted", HttpStatus.OK.value()));
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid RequestRegionDto requestRegionDto
    ) {
        regionService.updateById(requestRegionDto, id);

        return ResponseEntity.ok(new RestResponseDto("Region has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<RegionDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return regionService.getAllDto(rsqlQuery, sortQuery);
    }

    @ApiOperation(
            value = "",
            authorizations = @Authorization("ADMIN")
    )
    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestRegionDto requestRegionDto
    ) {
        regionService.add(requestRegionDto);

        return new ResponseEntity<>(new RestResponseDto("Region has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
