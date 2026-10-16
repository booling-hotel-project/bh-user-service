package ru.booking.hotel.application.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.booking.hotel.application.api.user.model.LoginUserRequest;
import ru.booking.hotel.application.api.user.model.LoginUserResponse;
import ru.booking.hotel.application.api.user.model.RegisterUserRequest;
import ru.booking.hotel.application.api.user.model.RegisterUserResponse;
import ru.booking.hotel.domain.error.BusinessException;
import ru.booking.hotel.domain.error.NotFoundException;
import ru.booking.hotel.domain.error.UnauthorizedException;
import ru.booking.hotel.application.infrastructure.mapping.UserMapper;
import ru.booking.hotel.domain.user.GuestProfile;
import ru.booking.hotel.domain.user.User;
import ru.booking.hotel.domain.user.UserRepository;
import ru.booking.hotel.domain.user.exception.UserErrorReason;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserApplicationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest) {
        log.info("Получен запрос на авторизацию пользователя c email: email={}", loginUserRequest.getEmail());

        User user = userRepository.findByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(UserErrorReason.UserNotFoundErrorReason.builder()
                        .email(loginUserRequest.getEmail())
                        .build()));

        if (!passwordEncoder.matches(loginUserRequest.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException(
                    UserErrorReason.UserInvalidPasswordErrorReason.builder().build()
            );

        }

        log.info("Пользователь с email: email={} , успешно зашел в систему", loginUserRequest.getEmail());

        return new LoginUserResponse()
                .userId(user.getId())
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(jwtService.generateRefreshToken(user.getId()))
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTtlSeconds());
    }

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        log.info("Получен запрос на регистрацию пользователя c email: email={}", registerUserRequest.getEmail());
        userRepository.findByEmail(registerUserRequest.getEmail())
                .ifPresent(existing -> {
                    throw new BusinessException(UserErrorReason.UserAlreadyExistsErrorReason.builder()
                            .email(registerUserRequest.getEmail())
                            .build());
                });

        User user = userMapper.toUser(registerUserRequest);
        user.setPasswordHash(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(user);

        GuestProfile profile = new GuestProfile();
        profile.setUserId(user.getId());
        userRepository.saveGuestProfile(profile);

        log.info("пользователь с email: email={}, успешно зарегистрировался", registerUserRequest.getEmail());

        return new RegisterUserResponse()
                .userId(user.getId())
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(jwtService.generateRefreshToken(user.getId()))
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTtlSeconds());
    }
}
