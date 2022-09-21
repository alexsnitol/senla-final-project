package ru.senla.realestatemarket.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Data
public class BalanceOperationWithoutUserIdDto {

    private Double sum;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdDt;

    private String comment;

}
