package com.ian.novelia.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 응답 데이터")
public class LoginResponseDto {

    @Schema(
            description = "액세스 토큰",
            example = "Bearer eyJhbGciOiJIUzI1NiJ9.example.token"
    )
    private String accessToken;
}
