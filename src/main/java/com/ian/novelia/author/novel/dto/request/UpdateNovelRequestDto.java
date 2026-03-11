package com.ian.novelia.author.novel.dto.request;

import com.ian.novelia.novel.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(description = "소설 등록 요청")
public class UpdateNovelRequestDto {

    @Schema(
            description = "변경할 소설 섬네일 이미지 파일",
            type = "string",
            format = "binary"
    )
    private MultipartFile thumbnail;

    @Schema(
            description = "변경할 소설 제목",
            example = "회귀한 마법사는 아카데미 천재였다"
    )
    private String title;

    @Schema(
            description = "변경할 소설 소개",
            example = "몰락한 천재 마법사가 과거로 돌아가 다시 아카데미에 입학하며 벌어지는 이야기입니다."
    )
    private String description;

    @Schema(
            description = "변경할 소설 카테고리",
            allowableValues = {"ROMANCE", "ROMANCE_FANTASY", "FANTASY", "BL"},
            example = "FANTASY"
    )
    private Category category;
}
