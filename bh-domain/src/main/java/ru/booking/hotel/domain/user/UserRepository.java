package ru.booking.hotel.domain.user;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.booking.hotel.domain.user.query.UserRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepository {

    EntityManager entityManager;
    NamedParameterJdbcTemplate jdbcTemplate;

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public GuestProfile saveGuestProfile(GuestProfile profile) {
        entityManager.persist(profile);
        return profile;
    }

    public Optional<User> findById(UUID id) {
        List<User> users = jdbcTemplate.query(
                "SELECT id, email, phone, password_hash, name, created_at FROM users WHERE id = :id",
                Map.of("id", id),
                new UserRowMapper()
        );
        return users.stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT id, email, phone, password_hash, name, created_at FROM users WHERE email = :email",
                Map.of("email", email),
                new UserRowMapper()
        );
        return users.stream().findFirst();
    }
}
