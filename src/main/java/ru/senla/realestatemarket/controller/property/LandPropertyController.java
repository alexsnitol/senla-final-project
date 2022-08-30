package ru.senla.realestatemarket.controller.property;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.property.LandPropertyDto;
import ru.senla.realestatemarket.dto.property.RequestLandPropertyDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.model.user.AuthorizedUser;
import ru.senla.realestatemarket.service.property.ILandPropertyService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/properties/lands")
public class LandPropertyController {

    private final ILandPropertyService landPropertyService;


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
        AuthorizedUser authorizedUser = (AuthorizedUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        landPropertyService.add(requestLandPropertyDto, authorizedUser.getId());

        return ResponseEntity.ok(new RestResponseDto("Land property added", HttpStatus.OK.value()));
    }

}
