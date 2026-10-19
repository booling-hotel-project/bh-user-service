package ru.booking.hotel.domain.role;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleRepository {

    EntityManager entityManager;
    NamedParameterJdbcTemplate jdbcTemplate;

    public UserRole save(UserRole userRole) {
        entityManager.persist(userRole);
        return userRole;
    }

    public List<String> findRoleNamesByUserId(UUID userId) {
        return jdbcTemplate.queryForList(
                "SELECT r.name FROM roles r JOIN user_roles ur ON ur.role_id = r.id WHERE ur.user_id = :userId",
                Map.of("userId", userId),
                String.class
        );
    }
}
