package ru.booking.hotel.domain.token;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRepository {

    EntityManager entityManager;

    public RefreshToken save(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        return refreshToken;
    }
}
