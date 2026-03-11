package com.ian.novelia.episode.controller;

import com.ian.novelia.episode.dto.response.GetEpisodeResponseDto;
import com.ian.novelia.episode.dto.response.GetEpisodesResponseDto;
import com.ian.novelia.episode.service.EpisodeService;
import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/novels/{novelId}/episodes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "Episode", description = "회차 관리 API")
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping
    @Operation(summary = "회차 목록 조회", description = "특정 소설의 공개된 회차 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<GetEpisodesResponseDto>>> getEpisodes(
            @PathVariable Long novelId,
            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetEpisodesResponseDto> response = episodeService.getEpisodes(novelId, pageable);
        return ResponseEntity.ok(ApiResponse.success("회차 목록 조회에 성공했습니다.", response));
    }

    @GetMapping("/{episodeId}")
    @Operation(summary = "회차 상세 조회", description = "구매한 회차의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<GetEpisodeResponseDto>> getEpisode(
            @PathVariable Long novelId,
            @PathVariable Long episodeId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        GetEpisodeResponseDto response = episodeService.getEpisode(novelId, episodeId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("회차 상세 조회에 성공했습니다.", response));
    }
}