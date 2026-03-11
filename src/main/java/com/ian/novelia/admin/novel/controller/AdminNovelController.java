package com.ian.novelia.admin.novel.controller;

import com.ian.novelia.admin.novel.dto.request.AdminNovelDeleteRequestDto;
import com.ian.novelia.admin.novel.service.AdminNovelService;
import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.search.NovelSearchCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/novels")
@RequiredArgsConstructor
@Tag(name = "Admin Novel", description = "관리자 소설 관리 API")
@PreAuthorize("hasRole('ADMIN')")
public class AdminNovelController {

    private final AdminNovelService adminNovelService;

    @GetMapping
    @Operation(summary = "전체 소설 목록 조회", description = "조건에 따라 소설 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<GetNovelResponseDto>>> getNovels(
            NovelSearchCondition searchCondition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetNovelResponseDto> response = adminNovelService.getNovels(searchCondition, pageable);
        return ResponseEntity.ok(ApiResponse.success("소설 목록 조회에 성공했습니다.", response));
    }

    @GetMapping("/{novelId}")
    @Operation(summary = "소설 상세 조회", description = "소설 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<GetNovelResponseDto>> getNovel(
            @PathVariable Long novelId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetNovelResponseDto response = adminNovelService.getNovel(novelId, userDetails);
        return ResponseEntity.ok(ApiResponse.success("소설 상세 조회에 성공했습니다.", response));
    }

    @DeleteMapping
    @Operation(summary = "소설 일괄 삭제", description = "여러 소설을 일괄 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteNovels(
            @Valid @RequestBody AdminNovelDeleteRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        adminNovelService.deleteNovels(request.getNovelIds(), userDetails);
        return ResponseEntity.ok(ApiResponse.success("소설 삭제에 성공했습니다.", null));
    }
}