package ru.booking.hotel.domain.user;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GuestProfileRepository {

    EntityManager entityManager;

    public GuestProfile save(GuestProfile profile) {
        entityManager.persist(profile);
        return profile;
    }
}
