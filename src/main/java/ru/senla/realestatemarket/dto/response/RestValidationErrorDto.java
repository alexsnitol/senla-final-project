package ru.senla.realestatemarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestValidationErrorDto {

    private String message;

    private Map<String, String> fieldErrorMessages;

    private String type;

    private Integer status;

    private String instance;

}
