package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestFamilyHousePropertyWithUserIdOfOwnerDto extends UpdateRequestFamilyHousePropertyDto {

    private Long userIdOfOwner;

}
