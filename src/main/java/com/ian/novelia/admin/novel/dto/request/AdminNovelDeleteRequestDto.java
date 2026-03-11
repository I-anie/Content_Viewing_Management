package com.ian.novelia.admin.novel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "관리자 소설 일괄 삭제 요청")
public class AdminNovelDeleteRequestDto {

    @NotEmpty
    @Schema(description = "삭제할 소설 ID 목록", example = "[1, 2, 3]")
    private List<Long> novelIds;
}