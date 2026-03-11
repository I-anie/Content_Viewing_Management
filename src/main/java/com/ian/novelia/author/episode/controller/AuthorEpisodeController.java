package com.ian.novelia.author.episode.controller;

import com.ian.novelia.author.episode.dto.request.CreateEpisodeRequestDto;
import com.ian.novelia.author.episode.dto.request.UpdateEpisodeRequestDto;
import com.ian.novelia.author.episode.service.AuthorEpisodeService;
import com.ian.novelia.episode.dto.response.GetEpisodeResponseDto;
import com.ian.novelia.episode.dto.response.GetEpisodesResponseDto;
import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
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
@RequestMapping("/author/novels/{novelId}/episodes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('AUTHOR')")
@Tag(name = "Author Episode", description = "작가 회차 관리 API")
public class AuthorEpisodeController {

    private final AuthorEpisodeService authorEpisodeService;

    @GetMapping
    @Operation(summary = "내 소설의 회차 목록 조회", description = "작가가 등록한 특정 소설의 회차 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<GetEpisodesResponseDto>>> getEpisodes(
            @PathVariable Long novelId,
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Page<GetEpisodesResponseDto> response = authorEpisodeService.getEpisodes(novelId, pageable, userDetails);
        return ResponseEntity.ok(ApiResponse.success("회차 목록 조회에 성공했습니다.", response));
    }

    @PostMapping
    @Operation(summary = "내 소설에 회차 등록", description = "작가가 등록한 특정 소설에 새로운 회차를 등록합니다.")
    public ResponseEntity<ApiResponse<GetEpisodeResponseDto>> createEpisode(
            @PathVariable Long novelId,
            @RequestBody @Valid CreateEpisodeRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetEpisodeResponseDto response = authorEpisodeService.createEpisode(novelId, request, userDetails);
        return ResponseEntity.ok(ApiResponse.success("회차 생성에 성공했습니다.", response));
    }

    @GetMapping("/{episodeId}")
    @Operation(summary = "내 소설의 회차 상세 조회", description = "작가가 등록한 특정 회차의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<GetEpisodeResponseDto>> getEpisode(
            @PathVariable Long novelId,
            @PathVariable Long episodeId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetEpisodeResponseDto response = authorEpisodeService.getEpisode(novelId, episodeId, userDetails);
        return ResponseEntity.ok(ApiResponse.success("회차 상세 조회에 성공했습니다.", response));
    }

    @PatchMapping("/{episodeId}")
    @Operation(summary = "내 소설의 회차 수정", description = "작가가 등록한 특정 회차 정보를 수정합니다.")
    public ResponseEntity<ApiResponse<GetEpisodeResponseDto>> updateEpisode(
            @PathVariable Long novelId,
            @PathVariable Long episodeId,
            @RequestBody @Valid UpdateEpisodeRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetEpisodeResponseDto response = authorEpisodeService.updateEpisode(novelId, episodeId, request, userDetails);
        return ResponseEntity.ok(ApiResponse.success("회차 수정에 성공했습니다.", response));
    }

    @DeleteMapping("/{episodeId}")
    @Operation(summary = "내 소설의 회차 삭제", description = "작가가 등록한 특정 회차를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteEpisode(
            @PathVariable Long novelId,
            @PathVariable Long episodeId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        authorEpisodeService.deleteEpisode(novelId, episodeId, userDetails);
        return ResponseEntity.ok(ApiResponse.success("회차 삭제에 성공했습니다.", null));
    }
}