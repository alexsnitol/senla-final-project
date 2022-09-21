package ru.senla.realestatemarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponseDto {

    private String message;

    private Integer status;

}
