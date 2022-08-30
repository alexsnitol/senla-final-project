package ru.senla.realestatemarket.dto.address;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestCityDto {

    @NotNull
    private Long regionId;

    @NotBlank
    @Size(max = 255)
    private String name;

}
