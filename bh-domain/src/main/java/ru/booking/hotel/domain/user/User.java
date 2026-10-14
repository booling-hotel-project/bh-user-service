package ru.booking.hotel.domain.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    UUID id;
    String email;
    String phone;
    String passwordHash;
    String name;
    OffsetDateTime createdAt;

}
