package ru.booking.hotel.application.config;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.booking.hotel.domain.user.UserRepository;

@Configuration
public class DomainConfig {

    @Bean
    public UserRepository userRepository(EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
        return new UserRepository(entityManager, jdbcTemplate);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
