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
public class CreateNovelResponseDto {

    @Schema(
            description = "소설 식별자",
            example = "3245"
    )
    private Long id;

    @Schema(
            description = "소설 섬네일 이미지 저장 경로",
            example = "https://novel-viewer-s3-image-bucket.s3.ap-southeast-2.amazonaws.com/e7f3b8a1-6c4d-4f92-9a5e-1b7c3d8f6a21.png"
    )
    private String thumbnailUrl;

    @Schema(
            description = "소설 제목",
            example = "회귀한 마법사는 아카데미 천재였다"
    )
    private String title;

    @Schema(
            description = "소설 소개",
            example = "몰락한 천재 마법사가 과거로 돌아가 다시 아카데미에 입학하며 벌어지는 이야기입니다."
    )
    private String description;

    @NotNull
    @Schema(
            description = "소설 카테고리",
            allowableValues = {"ROMANCE", "ROMANCE_FANTASY", "FANTASY", "BL"},
            example = "FANTASY"
    )
    private Category category;

    public static CreateNovelResponseDto from(Novel novel) {
        return CreateNovelResponseDto.builder()
                .id(novel.getId())
                .thumbnailUrl(novel.getThumbnailUrl())
                .title(novel.getTitle())
                .description(novel.getDescription())
                .build();
    }
}
