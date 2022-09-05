package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestFamilyHousePropertyWithUserIdOfOwnerDto extends RequestFamilyHousePropertyDto {

    @NotNull
    private Long userIdOfOwner;

}
