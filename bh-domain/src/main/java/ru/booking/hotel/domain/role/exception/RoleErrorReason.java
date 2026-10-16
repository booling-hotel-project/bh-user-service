package ru.booking.hotel.domain.role.exception;

import ru.booking.hotel.domain.error.AbstractErrorReason;
import ru.booking.hotel.domain.error.ErrorReason;

import lombok.Builder;

public abstract class RoleErrorReason extends AbstractErrorReason implements ErrorReason {

    @Builder
    public final static class RoleNotFoundErrorReason extends RoleErrorReason {
        public String roleName;
        {
            template("Роль с именем: ${roleName}, не найдена");
        }
    }
}
