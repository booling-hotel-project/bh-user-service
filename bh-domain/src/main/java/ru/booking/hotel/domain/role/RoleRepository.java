package ru.booking.hotel.domain.role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.booking.hotel.domain.role.query.RoleRowMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRepository {

    NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<Role> findByName(String name) {
        List<Role> roles = jdbcTemplate.query(
                "SELECT id, name, description FROM roles WHERE name = :name",
                Map.of("name", name),
                new RoleRowMapper()
        );
        return roles.stream().findFirst();
    }
}
