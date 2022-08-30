package ru.senla.realestatemarket.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorDto {

    private String message;

    private String type;

    private Integer status;

    private String instance;

}
