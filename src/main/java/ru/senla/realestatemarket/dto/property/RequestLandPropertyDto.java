package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.dto.address.RequestFullAddressWithoutHouseNumberDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestLandPropertyDto {

    private Float area;

    @NotNull
    private RequestFullAddressWithoutHouseNumberDto address;

}
