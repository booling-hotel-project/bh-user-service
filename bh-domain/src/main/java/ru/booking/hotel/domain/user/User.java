package ru.booking.hotel.domain.user;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    UUID id;
    String email;
    String phone;
    String passwordHash;
    String name;
    OffsetDateTime createdAt;

}
