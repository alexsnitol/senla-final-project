package ru.senla.realestatemarket.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RestValidationErrorDto {

    private String message;

    private Map<String, String> fieldErrorMessages;

    private String type;

    private Integer status;

    private String instance;

}
