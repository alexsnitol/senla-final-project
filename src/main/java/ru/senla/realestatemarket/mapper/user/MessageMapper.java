package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.senla.realestatemarket.dto.user.MessageDto;
import ru.senla.realestatemarket.dto.user.RequestMessageDto;
import ru.senla.realestatemarket.model.user.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Mapper
public abstract class MessageMapper {

    @Mapping(target = "createdDate", source = "message.createdDt")
    @Mapping(target = "createdTime", source = "message.createdDt")
    public abstract MessageDto toMessageDto(Message message);

    public abstract List<MessageDto> toMessageDto(Collection<Message> messages);

    public abstract Message requestMessageDtoToMessage(RequestMessageDto requestMessageDto);

    public LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    public LocalTime toLocalTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateMessageFromRequestMessageDto(
            RequestMessageDto requestMessageDto,
            @MappingTarget Message message
    );

}
