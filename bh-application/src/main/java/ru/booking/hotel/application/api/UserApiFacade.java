package ru.booking.hotel.application.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.booking.hotel.application.api.user.api.UserApiDelegate;
import ru.booking.hotel.application.api.user.model.LoginUserRequest;
import ru.booking.hotel.application.api.user.model.LoginUserResponse;
import ru.booking.hotel.application.api.user.model.RegisterUserRequest;
import ru.booking.hotel.application.api.user.model.RegisterUserResponse;
import ru.booking.hotel.application.service.user.UserApplicationService;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApiFacade implements UserApiDelegate {

    UserApplicationService userApplicationService;

    @Override
    public ResponseEntity<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        return ResponseEntity.ok(userApplicationService.registerUser(request));
    }

    @Override
    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRequest loginUserRequest) {
        return ResponseEntity.ok(userApplicationService.loginUser(loginUserRequest));
    }

}
