package com.ian.novelia.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원가입 요청")
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 6, max = 12)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]+$")
    @Schema(
            description = "아이디. 영소문자와 숫자를 각각 1개 이상 포함하여 생성해야 합니다. 이미 등록된 아이디는 사용할 수 없습니다.",
            example = "asdf1234",
            minLength = 6,
            maxLength = 12,
            pattern = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]+$"
    )
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-z\\d!@#$%^&*]+$")
    @Schema(
            description = "비밀번호. 영소문자, 숫자, 특수문자(!@#$%^&*)를 각각 1개 이상 포함하여 생성해야 합니다.",
            example = "@asdf1234!",
            minLength = 8,
            maxLength = 20,
            pattern = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-z\\d!@#$%^&*]+$"
    )
    private String password;

    @NotBlank
    @Schema(
            description = "비밀번호 확인. 비밀번호와 일치해야 합니다."
    )
    private String confirmPassword;

    @NotBlank
    @Email
    @Schema(
            description = "이메일. 이미 등록된 이메일은 사용할 수 없습니다.",
            example = "asdf1234@example.com"
    )
    private String email;
}
