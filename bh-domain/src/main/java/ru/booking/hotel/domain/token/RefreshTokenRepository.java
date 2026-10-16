package ru.booking.hotel.domain.token;

import java.util.UUID;

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

    public void deleteByUserId(UUID userId) {
        entityManager.createQuery("DELETE FROM RefreshToken t WHERE t.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
