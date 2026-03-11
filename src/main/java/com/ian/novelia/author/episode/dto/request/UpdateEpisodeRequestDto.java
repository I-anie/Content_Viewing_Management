package com.ian.novelia.author.episode.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회차 수정 요청")
public class UpdateEpisodeRequestDto {

    @Schema(
            description = "변경할 회차 제목",
            example = "제 1장. 다시 열린 아카데미의 문"
    )
    private String title;

    @Schema(
            description = "변경할 회차 내용",
            example = "눈을 뜬 순간, 익숙하면서도 믿기 힘든 풍경이 눈앞에 펼쳐졌다. 높게 솟은 흰 첨탑, 정교하게 다듬어진 석조 복도, 그리고 ..."
    )
    private String content;
}
