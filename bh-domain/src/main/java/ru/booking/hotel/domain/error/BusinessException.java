package ru.booking.hotel.domain.error;

public class BusinessException extends RuntimeException {

    private final ErrorReason errorReason;

    public BusinessException(ErrorReason reason) {
        super(reason.getMessage());
        this.errorReason = reason;
    }


    public ErrorReason getReason() { return errorReason; }

}
