package ru.booking.hotel.domain.user;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestProfile {

    UUID userId;
    String passport;
    String loyaltyLevel;
    JsonNode preferences;

}
