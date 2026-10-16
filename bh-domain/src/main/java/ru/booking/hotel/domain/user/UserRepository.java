package ru.booking.hotel.domain.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.booking.hotel.domain.user.query.UserRowMapper;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepository {

    EntityManager entityManager;
    NamedParameterJdbcTemplate jdbcTemplate;

    public User saveUser(User user) {
        entityManager.persist(user);
        return user;
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
