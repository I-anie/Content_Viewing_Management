package com.ian.novelia.purchase.controller;

import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novels/{novelId}/episodes/{episodeId}/purchases")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "Purchase", description = "회차 구매 관리 API")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    @Operation(summary = "회차 구매", description = "일반 회원이 특정 소설의 회차를 구매합니다.")
    public ResponseEntity<ApiResponse<Void>> purchaseEpisode(
            @PathVariable Long novelId,
            @PathVariable Long episodeId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        purchaseService.purchaseEpisode(novelId, episodeId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("회차 구매에 성공했습니다.", null));
    }
}