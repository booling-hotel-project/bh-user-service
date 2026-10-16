package ru.booking.hotel.domain.error;

import java.util.Map;

public interface ErrorReason {

    String getCode();
    String getMessage();
    Map<String, Object> getParameters();

}
