package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestBalanceOperationDto {

    @NotNull
    private Double sum;

    @Size(min = 1, max = 255)
    private String comment;

}
