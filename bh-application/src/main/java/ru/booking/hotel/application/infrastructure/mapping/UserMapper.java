package ru.booking.hotel.application.infrastructure.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.booking.hotel.application.api.user.model.RegisterUserRequest;
import ru.booking.hotel.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    User toUser(RegisterUserRequest request);
}
