package ru.senla.realestatemarket.mapper.restresponse;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import ru.senla.realestatemarket.dto.response.RestErrorDto;
import ru.senla.realestatemarket.dto.response.RestResponseDto;
import ru.senla.realestatemarket.dto.response.RestValidationErrorDto;

import java.util.Map;

@Mapper
public class RestResponseMapper {

    public RestResponseDto toRestResponseDto(String message, HttpStatus httpStatus) {
        RestResponseDto restResponseDto = new RestResponseDto();

        restResponseDto.setMessage(message);
        restResponseDto.setStatus(httpStatus.value());

        return restResponseDto;
    }

    public RestErrorDto toRestErrorDto(String message, String type, Integer status, String instance) {
        RestErrorDto restErrorDto = new RestErrorDto();

        restErrorDto.setMessage(message);
        restErrorDto.setType(type);
        restErrorDto.setStatus(status);
        restErrorDto.setInstance(instance);

        return restErrorDto;
    }

    public RestValidationErrorDto toRestValidationErrorDto(Map<String, String> fieldErrorMessages, String type,
                                                           Integer status, String instance) {
        RestValidationErrorDto restValidationErrorDto = new RestValidationErrorDto();

        restValidationErrorDto.setFieldErrorMessages(fieldErrorMessages);
        restValidationErrorDto.setType(type);
        restValidationErrorDto.setStatus(status);
        restValidationErrorDto.setInstance(instance);

        return restValidationErrorDto;
    }

}
