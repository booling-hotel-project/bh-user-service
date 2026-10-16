package ru.booking.hotel.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ru.booking.hotel.application.api.user.api.UserApiDelegate;
import ru.booking.hotel.application.api.user.model.LoginUserRequest;
import ru.booking.hotel.application.api.user.model.LoginUserResponse;
import ru.booking.hotel.application.api.user.model.RegisterUserRequest;
import ru.booking.hotel.application.api.user.model.RegisterUserResponse;
import ru.booking.hotel.application.service.user.UserApplicationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApiFacade implements UserApiDelegate {

    UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userApplicationService.registerUser(request));
    }

    @Override
    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRequest loginUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userApplicationService.loginUser(loginUserRequest));
    }

}
