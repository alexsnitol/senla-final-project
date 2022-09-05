package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequestStreetDto {

    private Long cityId;

    @Size(min = 1, max = 255)
    private String name;

}
