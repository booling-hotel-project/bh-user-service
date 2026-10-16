package ru.booking.hotel.domain.error;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorReason reason) {
        super(reason);
    }

}
