package ru.booking.hotel.domain.role;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleRepository {

    EntityManager entityManager;

    public UserRole save(UserRole userRole) {
        entityManager.persist(userRole);
        return userRole;
    }
}
