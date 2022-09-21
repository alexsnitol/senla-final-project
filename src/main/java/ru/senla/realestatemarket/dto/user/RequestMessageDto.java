package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Getter
@Setter
@ToString
public class RequestMessageDto {

    @Size(min = 1, max = 1023)
    @NotBlank
    private String text;

}
