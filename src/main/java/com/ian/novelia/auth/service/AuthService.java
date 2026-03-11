package com.ian.novelia.auth.service;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.auth.dto.request.LoginRequestDto;
import com.ian.novelia.auth.dto.request.RegisterRequestDto;
import com.ian.novelia.auth.dto.response.LoginResponseDto;
import com.ian.novelia.auth.repository.AuthRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ian.novelia.global.error.code.ErrorCode.PASSWORD_CONFIRM_MISMATCH;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void register(RegisterRequestDto request) {
        validatePasswordConfirm(request);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        authRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String accessToken = jwtProvider.generateToken(authentication);
        return new LoginResponseDto(accessToken);
    }

    private void validatePasswordConfirm(RegisterRequestDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new CustomException(PASSWORD_CONFIRM_MISMATCH);
        }
    }
}