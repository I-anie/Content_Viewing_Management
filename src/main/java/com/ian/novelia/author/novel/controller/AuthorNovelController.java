package com.ian.novelia.author.novel.controller;

import com.ian.novelia.author.novel.dto.request.CreateNovelRequestDto;
import com.ian.novelia.author.novel.dto.request.UpdateNovelRequestDto;
import com.ian.novelia.author.novel.dto.response.CreateNovelResponseDto;
import com.ian.novelia.author.novel.dto.response.UpdateNovelResponseDto;
import com.ian.novelia.author.novel.service.AuthorNovelService;
import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.search.NovelSearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/author/novels")
@RequiredArgsConstructor
@Tag(name = "Author Novel", description = "작가 소설 관리 API")
@PreAuthorize("hasRole('AUTHOR')")
public class AuthorNovelController {

    private final AuthorNovelService novelService;

    @GetMapping
    @Operation(
            summary = "내 소설 목록 조회",
            description = "작가가 등록한 소설 목록을 조회합니다. 제목, 소개, 카테고리 등의 조건으로 검색할 수 있습니다."
    )
    public ResponseEntity<ApiResponse<Page<GetNovelResponseDto>>> getNovels(
            NovelSearchCondition searchCondition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "소설 목록 조회에 성공했습니다.",
                novelService.getNovels(searchCondition, pageable, userDetails)
        ));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "소설 등록",
            description = "작가가 새로운 소설을 등록합니다."
    )
    public ResponseEntity<ApiResponse<CreateNovelResponseDto>> createNovel(
            @Valid CreateNovelRequestDto request, @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "소설 등록에 성공했습니다.",
                novelService.createNovel(request, userDetails.getUsername())
        ));
    }

    @GetMapping("/{novelId}")
    @Operation(
            summary = "내 소설 상세 조회",
            description = "작가가 등록한 특정 소설의 상세 정보를 조회합니다. 본인이 작성한 소설만 조회할 수 있습니다."
    )
    public ResponseEntity<ApiResponse<GetNovelResponseDto>> getNovel(
            @PathVariable Long novelId, @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "소설 상세 조회에 성공했습니다.",
                novelService.getNovel(novelId, userDetails.getUsername())
        ));
    }

    @PatchMapping("/{novelId}")
    @Operation(
            summary = "내 소설 수정",
            description = "작가가 등록한 소설 정보를 수정합니다."
    )
    public ResponseEntity<ApiResponse<UpdateNovelResponseDto>> updateNovel(
            @PathVariable Long novelId, UpdateNovelRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "소설 수정에 성공했습니다.",
                novelService.updateNovel(novelId, request, userDetails.getUsername())
        ));
    }

    @DeleteMapping("/{novelId}")
    @Operation(
            summary = "내 소설 삭제",
            description = "작가가 등록한 소설을 삭제합니다."
    )
    public ResponseEntity<ApiResponse<?>> deleteNovel(
            @PathVariable Long novelId, @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        novelService.deleteNovel(novelId, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.success(
                "소설 삭제에 성공했습니다.",
                null
        ));
    }
}
