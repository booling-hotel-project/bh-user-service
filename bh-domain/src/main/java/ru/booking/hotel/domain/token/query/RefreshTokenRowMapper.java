package ru.booking.hotel.domain.token.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import ru.booking.hotel.domain.token.RefreshToken;
import ru.booking.hotel.domain.token.TokenType;

public class RefreshTokenRowMapper implements RowMapper<RefreshToken> {

    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RefreshToken.builder()
                .id(rs.getObject("id", UUID.class))
                .userId(rs.getObject("user_id", UUID.class))
                .tokenHash(rs.getString("token_hash"))
                .tokenType(TokenType.valueOf(rs.getString("token_type")))
                .expiresAt(rs.getObject("expires_at", OffsetDateTime.class))
                .build();
    }
}
