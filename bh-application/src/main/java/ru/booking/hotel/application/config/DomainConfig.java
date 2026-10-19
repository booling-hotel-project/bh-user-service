package ru.booking.hotel.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.booking.hotel.domain.role.RoleRepository;
import ru.booking.hotel.domain.role.UserRoleRepository;
import ru.booking.hotel.domain.token.RefreshTokenRepository;
import ru.booking.hotel.domain.user.GuestProfileRepository;
import ru.booking.hotel.domain.user.StaffProfileRepository;
import ru.booking.hotel.domain.user.UserRepository;

import jakarta.persistence.EntityManager;

@Configuration
public class DomainConfig {

    @Bean
    public UserRepository userRepository(EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
        return new UserRepository(entityManager, jdbcTemplate);
    }

    @Bean
    public GuestProfileRepository guestProfileRepository(EntityManager entityManager) {
        return new GuestProfileRepository(entityManager);
    }

    @Bean
    public StaffProfileRepository staffProfileRepository(EntityManager entityManager) {
        return new StaffProfileRepository(entityManager);
    }

    @Bean
    public RefreshTokenRepository refreshTokenRepository(
            EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
        return new RefreshTokenRepository(entityManager, jdbcTemplate);
    }

    @Bean
    public RoleRepository roleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new RoleRepository(jdbcTemplate);
    }

    @Bean
    public UserRoleRepository userRoleRepository(EntityManager entityManager) {
        return new UserRoleRepository(entityManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
