package com.ian.novelia.author.novel.dto.response;

import com.ian.novelia.novel.domain.Category;
import com.ian.novelia.novel.domain.Novel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "소설 등록 요청 응답")
public class UpdateNovelResponseDto {

    @Schema(
            description = "소설 식별자",
            example = "3245"
    )
    private Long id;

    @Schema(
            description = "변경한 소설 섬네일 이미지 저장 경로",
            example = "https://novel-viewer-s3-image-bucket.s3.ap-southeast-2.amazonaws.com/7f3c2a91-5d84-4b7e-9c16-2a8f4d6b1e73.png"
    )
    private String thumbnailUrl;

    @Schema(
            description = "변경한 소설 제목",
            example = "회귀한 마법사는 사실 아카데미의 천재였다"
    )
    private String title;

    @Schema(
            description = "변경한 소설 소개",
            example = "몰락한 천재 마법사가 과거로 돌아가 다시 아카데미에 입학하며 벌어지는 먼치킨 이야기입니다."
    )
    private String description;

    @NotNull
    @Schema(
            description = "변경한 소설 카테고리",
            allowableValues = {"ROMANCE", "ROMANCE_FANTASY", "FANTASY", "BL"},
            example = "FANTASY"
    )
    private Category category;

    public static UpdateNovelResponseDto from(Novel novel) {
        return UpdateNovelResponseDto.builder()
                .id(novel.getId())
                .thumbnailUrl(novel.getThumbnailUrl())
                .title(novel.getTitle())
                .description(novel.getDescription())
                .build();
    }
}
