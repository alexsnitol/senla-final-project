package ru.senla.realestatemarket.controller.property;

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
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.property.UpdateRequestLandPropertyDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.service.property.ILandPropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/lands")
public class LandPropertyController {

    private final ILandPropertyService landPropertyService;


    @GetMapping("/{id}")
    public LandPropertyDto getById(
            @PathVariable Long id
    ) {
        return landPropertyService.getDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponseDto> deleteById(
            @PathVariable Long id
    ) {
        landPropertyService.deleteById(id);

        return ResponseEntity.ok(new RestResponseDto("Land property has been deleted", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponseDto> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestLandPropertyDto updateRequestLandPropertyDto
    ) {
        landPropertyService.updateById(updateRequestLandPropertyDto, id);

        return ResponseEntity.ok(new RestResponseDto("Land property has been updated", HttpStatus.OK.value()));
    }

    @GetMapping
    public List<LandPropertyDto> getAllLandProperties(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landPropertyService.getAllDto(rsqlQuery, sortQuery);
    }

    @PostMapping
    public ResponseEntity<RestResponseDto> addLandProperty(
            @RequestBody @Valid RequestLandPropertyDto requestLandPropertyDto
    ) {
        landPropertyService.addFromCurrentUser(requestLandPropertyDto);

        return new ResponseEntity<>(new RestResponseDto("Land property has been added",
                HttpStatus.OK.value()), HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public List<LandPropertyDto> getAllDtoOfCurrentUser(
            @RequestParam(value = "q",required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return landPropertyService.getAllDtoOfCurrentUser(rsqlQuery, sortQuery);
    }

}
