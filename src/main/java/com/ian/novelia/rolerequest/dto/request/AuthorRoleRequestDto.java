package com.ian.novelia.rolerequest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "작가 권한 요청")
public class AuthorRoleRequestDto {

    @NotBlank
    @Schema(
            description = "필명. 작품 활동에 사용할 필명을 등록합니다.",
            example = "나혼자만렙작가"
    )
    private String penName;
}
