package ru.senla.realestatemarket.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class RequestBalanceOperationDto {

    @NotNull
    private Double sum;

    @Size(min = 1, max = 255)
    private String comment;

}
