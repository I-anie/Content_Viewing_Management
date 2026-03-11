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
@Schema(description = "회차 목록 조회 응답")
public class GetEpisodesResponseDto {

    @Schema(
            description = "회차 제목",
            example = "제 1장. 다시 시작된 입학식"
    )
    private String title;

    public static GetEpisodesResponseDto from(Episode episode) {
        return GetEpisodesResponseDto.builder()
                .title(episode.getTitle())
                .build();
    }
}
