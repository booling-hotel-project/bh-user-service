package ru.booking.hotel.domain.token;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.booking.hotel.domain.token.query.RefreshTokenRowMapper;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRepository {

    EntityManager entityManager;
    NamedParameterJdbcTemplate jdbcTemplate;

    public RefreshToken save(RefreshToken refreshToken) {
        entityManager.persist(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByTokenHash(String tokenHash) {
        List<RefreshToken> tokens = jdbcTemplate.query(
                "SELECT id, user_id, token_hash, token_type, expires_at FROM refresh_tokens WHERE token_hash = :tokenHash",
                Map.of("tokenHash", tokenHash),
                new RefreshTokenRowMapper()
        );
        return tokens.stream().findFirst();
    }

    public void deleteByUserId(UUID userId) {
        entityManager.createQuery("DELETE FROM RefreshToken t WHERE t.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
