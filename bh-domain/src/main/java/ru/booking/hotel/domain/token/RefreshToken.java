package ru.booking.hotel.domain.token;

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
public class RefreshToken {

    UUID id;
    UUID userId;
    String tokenHash;
    TokenType tokenType;
    OffsetDateTime expiresAt;

}
