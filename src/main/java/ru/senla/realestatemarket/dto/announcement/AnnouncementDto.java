package ru.senla.realestatemarket.dto.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.AnnouncementStatusEnum;
import ru.senla.realestatemarket.model.property.PropertyTypeEnum;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnnouncementDto {

    private Long id;

    private Double price;

    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime createdDt;

    private AnnouncementStatusEnum status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PropertyTypeEnum propertyType;

    private Boolean top;

}
