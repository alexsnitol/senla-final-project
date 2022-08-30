package ru.senla.realestatemarket.controller.property;

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
import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.service.property.IRenovationTypeService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/renovation-types")
public class RenovationTypeController {

    private final IRenovationTypeService renovationTypeService;


    @GetMapping
    public List<RenovationType> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return renovationTypeService.getAll(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> add(
            @RequestBody @Valid RequestRenovationTypeDto requestRenovationTypeDto
    ) {
        renovationTypeService.add(requestRenovationTypeDto);

        return ResponseEntity.ok(new RestResponseDto("Renovation type added", HttpStatus.OK.value()));
    }


}
