package ru.booking.hotel.domain.role.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import ru.booking.hotel.domain.role.Role;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getObject("id", UUID.class));
        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        return role;
    }
}
