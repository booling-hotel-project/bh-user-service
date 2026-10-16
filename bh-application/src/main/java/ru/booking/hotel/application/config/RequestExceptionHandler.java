package ru.booking.hotel.application.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.booking.hotel.application.api.user.model.ApiError;
import ru.booking.hotel.application.api.user.model.FieldViolation;
import ru.booking.hotel.application.api.user.model.ValidationError;
import ru.booking.hotel.domain.error.BusinessException;
import ru.booking.hotel.domain.error.NotFoundException;
import ru.booking.hotel.domain.error.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        log.warn("Ресурс не найден: {}", ex.getMessage());
        return status(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        log.warn("Ошибка авторизации: {}", ex.getMessage());
        return status(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {
        log.warn("Бизнес-ошибка: {}", ex.getMessage());
        return status(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldViolation()
                        .field(error.getField())
                        .message(error.getDefaultMessage()))
                .toList();
        log.warn("Ошибка валидации: {}", violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationError()
                        .code("VALIDATION_FAILED")
                        .message("Ошибка валидации запроса")
                        .violations(violations));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        log.error("Непредвиденная ошибка", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError()
                        .code("INTERNAL_ERROR")
                        .message("Внутренняя ошибка сервера"));
    }

    private ResponseEntity<ApiError> status(HttpStatus status, BusinessException ex) {
        return ResponseEntity.status(status)
                .body(new ApiError()
                        .code(ex.getReason().getCode())
                        .message(ex.getMessage()));
    }
}
