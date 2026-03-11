package com.ian.novelia.episode.dto.response;

import com.ian.novelia.episode.domain.Episode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회차 상세 조회 응답")
public class GetEpisodeResponseDto {

    @Schema(
            description = "회차 제목",
            example = "제 1장. 다시 시작된 입학식"
    )
    private String title;

    @Schema(
            description = "회차 제목",
            example = "눈을 뜬 순간, 익숙하면서도 믿기 힘든 풍경이 눈앞에 펼쳐졌다. 높게 솟은 흰 첨탑, 정교하게 다듬어진 석조 복도, 그리고 ..."
    )
    private String content;

    public static GetEpisodeResponseDto from(Episode episode) {
        return GetEpisodeResponseDto.builder()
                .title(episode.getTitle())
                .content(episode.getContent())
                .build();
    }
}
