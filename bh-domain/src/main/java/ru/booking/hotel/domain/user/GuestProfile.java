package ru.booking.hotel.domain.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestProfile {

    UUID userId;
    String passport;
    String loyaltyLevel;
    String preferences;

}
