package ru.booking.hotel.domain.error;

public class NotFoundException extends BusinessException {

    public NotFoundException(final ErrorReason reason) {
        super(reason);
    }

}
