package com.ian.novelia.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그인 요청")
public class LoginRequestDto {

    @NotBlank
    @Schema(
            description = "회원가입 시 등록한 아이디",
            example = "asdf1234"
    )
    private String username;

    @NotBlank
    @Schema(
            description = "회원가입 시 등록한 비밀번호",
            example = "@asdf1234!"
    )
    private String password;
}