package com.ian.novelia.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "NOVEL_404")
    private final String code;

    @Schema(description = "에러 메시지", example = "일치하는 소설 정보를 찾을 수 없습니다.")
    private final String message;
}