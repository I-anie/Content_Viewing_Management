package com.ian.novelia.novel.controller;

import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.search.NovelSearchCondition;
import com.ian.novelia.novel.service.NovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
@Tag(name = "Novel", description = "소설 관리 API")
public class NovelController {

    private final NovelService novelService;

    @GetMapping
    @Operation(
            summary = "소설 목록 조회",
            description = "공개된 소설 목록을 조회합니다. 제목, 소개, 작가명, 카테고리 조건으로 검색할 수 있습니다."
    )
    public ResponseEntity<ApiResponse<Page<GetNovelResponseDto>>> getNovels(
            NovelSearchCondition searchCondition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetNovelResponseDto> response = novelService.getNovels(searchCondition, pageable);
        return ResponseEntity.ok(ApiResponse.success("소설 목록 조회에 성공했습니다.", response));
    }

    @GetMapping("/{novelId}")
    @Operation(
            summary = "소설 상세 조회",
            description = "공개된 소설의 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<GetNovelResponseDto>> getNovel(@PathVariable Long novelId) {
        GetNovelResponseDto response = novelService.getNovel(novelId);
        return ResponseEntity.ok(ApiResponse.success("소설 상세 조회에 성공했습니다.", response));
    }
}