package ru.booking.hotel.domain.user.exception;

import ru.booking.hotel.domain.error.AbstractErrorReason;
import ru.booking.hotel.domain.error.ErrorReason;

import lombok.Builder;

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

    @Builder
    public final static class RefreshTokenNotFoundErrorReason extends UserErrorReason {
        {
            template("Refresh-токен не найден");
        }
    }

    @Builder
    public final static class RefreshTokenExpiredErrorReason extends UserErrorReason {
        {
            template("Срок действия refresh-токена истек");
        }
    }
}
