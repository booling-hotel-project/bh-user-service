package ru.booking.hotel.domain.user.exception;

import lombok.Builder;
import ru.booking.hotel.domain.error.AbstractErrorReason;
import ru.booking.hotel.domain.error.ErrorReason;

public abstract class UserErrorReason extends AbstractErrorReason implements ErrorReason {

    @Builder
    public final static class UserNotFoundErrorReason extends UserErrorReason {
        public String email;
        {
            template("Пользователь с почтой: ${email}, не найден");
        }
    }

    @Builder
    public final static class UserInvalidPasswordErrorReason extends UserErrorReason {
        {
            template("Неправильный пароль");
        }
    }

    @Builder
    public final static class UserAlreadyExistsErrorReason extends UserErrorReason {
        public String email;
        {
            template("Пользователь с почтой: ${email}, уже существует");
        }
    }
}
