package com.ian.novelia.rolerequest.controller;

import com.ian.novelia.global.response.ApiResponse;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.rolerequest.dto.request.AuthorRoleRequestDto;
import com.ian.novelia.rolerequest.dto.response.AuthorRoleResponseDto;
import com.ian.novelia.rolerequest.service.RoleRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role-requests")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "RoleRequest", description = "권한 요청 관리 API")
public class RoleRequestController {

    private final RoleRequestService roleRequestService;

    @PostMapping("/author")
    @Operation(
            summary = "작가 신청",
            description = "작가 권한 승인을 요청합니다. 작품에 사용할 필명을 함께 등록해야 합니다."
    )
    public ResponseEntity<ApiResponse<AuthorRoleResponseDto>> authorRole(
            @RequestBody @Valid AuthorRoleRequestDto request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        AuthorRoleResponseDto response = roleRequestService.authorRole(request.getPenName(), userDetails);
        return ResponseEntity.ok(ApiResponse.success("작가 권한 요청에 성공했습니다.", response));
    }
}