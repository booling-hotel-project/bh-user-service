package ru.booking.hotel.application.service.user;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.booking.hotel.application.api.user.model.LoginUserRequest;
import ru.booking.hotel.application.api.user.model.LoginUserResponse;
import ru.booking.hotel.application.api.user.model.RegisterUserRequest;
import ru.booking.hotel.application.api.user.model.RegisterUserResponse;
import ru.booking.hotel.application.infrastructure.mapping.UserMapper;
import ru.booking.hotel.application.infrastructure.security.TokenHasher;
import ru.booking.hotel.domain.error.BusinessException;
import ru.booking.hotel.domain.error.NotFoundException;
import ru.booking.hotel.domain.error.UnauthorizedException;
import ru.booking.hotel.domain.role.Role;
import ru.booking.hotel.domain.role.RoleRepository;
import ru.booking.hotel.domain.role.UserRole;
import ru.booking.hotel.domain.role.UserRoleRepository;
import ru.booking.hotel.domain.role.UserRoles;
import ru.booking.hotel.domain.role.exception.RoleErrorReason;
import ru.booking.hotel.domain.token.RefreshToken;
import ru.booking.hotel.domain.token.RefreshTokenRepository;
import ru.booking.hotel.domain.user.GuestProfile;
import ru.booking.hotel.domain.user.GuestProfileRepository;
import ru.booking.hotel.domain.user.User;
import ru.booking.hotel.domain.user.UserRepository;
import ru.booking.hotel.domain.user.exception.UserErrorReason;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserApplicationService {

    UserRepository userRepository;
    GuestProfileRepository guestProfileRepository;
    RefreshTokenRepository refreshTokenRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    TokenHasher tokenHasher;
    UserMapper userMapper;
    UserRoleRepository userRoleRepository;
    RoleRepository roleRepository;

    @Transactional
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

        String rawRefreshToken = issueRefreshToken(user.getId());

        log.info("Пользователь с email={} , успешно зашел в систему", loginUserRequest.getEmail());

        return new LoginUserResponse()
                .userId(user.getId())
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(rawRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTtlSeconds());
    }

    @Transactional
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        log.info("Получен запрос на регистрацию пользователя c email={}", registerUserRequest.getEmail());
        userRepository.findByEmail(registerUserRequest.getEmail())
                .ifPresent(existing -> {
                    throw new BusinessException(UserErrorReason.UserAlreadyExistsErrorReason.builder()
                            .email(registerUserRequest.getEmail())
                            .build());
                });

        User user = userMapper.toUser(registerUserRequest);
        user.setPasswordHash(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.saveUser(user);

        Role userRole = roleRepository.findByName(UserRoles.USER.getName())
                .orElseThrow(() -> new NotFoundException(
                        RoleErrorReason.RoleNotFoundErrorReason.builder()
                                .roleName(UserRoles.USER.getName())
                                .build()
                ));

        UserRole link = UserRole.builder()
                .userId(user.getId())
                .roleId(userRole.getId())
                .build();
        userRoleRepository.save(link);

        GuestProfile profile = new GuestProfile();
        profile.setUserId(user.getId());
        guestProfileRepository.save(profile);

        String rawRefreshToken = issueRefreshToken(user.getId());

        log.info("Пользователь с email={}, успешно зарегистрировался", registerUserRequest.getEmail());

        return new RegisterUserResponse()
                .userId(user.getId())
                .accessToken(jwtService.generateAccessToken(user.getId()))
                .refreshToken(rawRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTtlSeconds());
    }

    private String issueRefreshToken(UUID userId) {
        String rawRefreshToken = jwtService.generateRefreshToken(userId);
        var refreshToken = RefreshToken.builder()
                .userId(userId)
                .tokenHash(tokenHasher.sha256Hex(rawRefreshToken))
                .expiresAt(OffsetDateTime.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .build();

        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.save(refreshToken);
        return rawRefreshToken;
    }
}
