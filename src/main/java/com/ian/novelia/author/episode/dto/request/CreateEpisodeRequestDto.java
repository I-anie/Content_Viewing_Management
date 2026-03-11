package com.ian.novelia.author.episode.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회차 생성 요청")
public class CreateEpisodeRequestDto {

    @NotBlank
    @Schema(
            description = "회차 제목",
            example = "제 1장. 다시 시작된 입학식"
    )
    private String title;

    @NotBlank
    @Schema(
            description = "회차 제목",
            example = "눈을 뜬 순간, 익숙하면서도 믿기 힘든 풍경이 눈앞에 펼쳐졌다. 높게 솟은 흰 첨탑, 정교하게 다듬어진 석조 복도, 그리고 ..."
    )
    private String content;
}
