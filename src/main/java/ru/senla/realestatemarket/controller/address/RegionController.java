package ru.senla.realestatemarket.controller.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final IRegionService regionService;


    @GetMapping
    public List<RegionDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return regionService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestRegionDto requestRegionDto
    ) {
        regionService.add(requestRegionDto);

        return ResponseEntity.ok(new RestResponseDto("Region added", HttpStatus.OK.value()));
    }

}
