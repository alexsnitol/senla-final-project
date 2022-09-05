package ru.senla.realestatemarket.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestLandPropertyWithUserIdOfOwnerDto extends UpdateRequestLandPropertyDto {

    private Long userIdOfOwner;

}
