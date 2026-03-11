package com.ian.novelia.auth.controller;

import com.ian.novelia.auth.dto.request.LoginRequestDto;
import com.ian.novelia.auth.dto.request.RegisterRequestDto;
import com.ian.novelia.auth.dto.response.LoginResponseDto;
import com.ian.novelia.auth.service.AuthService;
import com.ian.novelia.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 및 인가 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "사용자 정보를 입력받아 회원가입을 진행합니다.")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid RegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입에 성공했습니다.", null));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인하고 액세스 토큰을 반환합니다.")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("로그인에 성공했습니다.", response));
    }
}