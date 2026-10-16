package ru.booking.hotel.domain.user;

import java.time.LocalDate;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffProfile {

    UUID userId;
    String department;
    String position;
    LocalDate hireDate;

}
