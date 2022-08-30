package ru.senla.realestatemarket.controller.house;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.dto.house.HouseDto;
import ru.senla.realestatemarket.service.house.IHouseService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final IHouseService houseService;


    @GetMapping
    public List<HouseDto> getAll(
            @RequestParam(value = "q", required = false) String rsqlQuery,
            @RequestParam(value = "sort", required = false) String sortQuery
    ) {
        return houseService.getAllDto(rsqlQuery, sortQuery);
    }

}
