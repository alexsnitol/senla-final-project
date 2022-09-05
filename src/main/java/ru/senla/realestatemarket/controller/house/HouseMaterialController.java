package ru.senla.realestatemarket.controller.house;

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
import ru.senla.realestatemarket.dto.house.RequestHouseMaterialDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.house.HouseMaterial;
import ru.senla.realestatemarket.service.house.IHouseMaterialService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/house-materials")
public class HouseMaterialController {

    private final IHouseMaterialService houseMaterialService;


    @GetMapping("/{id}")
    public HouseMaterial getById(
            @PathVariable Long id
    ) {
        return houseMaterialService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        houseMaterialService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("House material has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid RequestHouseMaterialDto requestHouseMaterialDto
    ) {
        houseMaterialService.updateById(requestHouseMaterialDto, id);

        return ResponseEntity.ok(new RestResponseDto("House material has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<HouseMaterial> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return houseMaterialService.getAll(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestHouseMaterialDto requestHouseMaterialDto
    ) {
        houseMaterialService.add(requestHouseMaterialDto);

        return new ResponseEntity<>(new RestResponseDto("House material has been added",
                HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

}
