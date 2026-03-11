package com.ian.novelia.admin.rolerequest.controller;

import com.ian.novelia.admin.rolerequest.service.AdminRoleRequestService;
import com.ian.novelia.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/role-requests")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "RoleRequest", description = "작가 권한 요청 관리 API")
public class AdminRoleRequestController {

    private final AdminRoleRequestService adminRoleRequestService;

    @PatchMapping("/{requestId}/approve")
    @Operation(summary = "작가 권한 승인", description = "사용자의 작가 권한 요청을 승인합니다.")
    public ResponseEntity<ApiResponse<Void>> approve(@PathVariable Long requestId) {
        adminRoleRequestService.approve(requestId);
        return ResponseEntity.ok(ApiResponse.success("작가 권한 요청을 승인했습니다.", null));
    }

    @PatchMapping("/{requestId}/reject")
    @Operation(summary = "작가 권한 거절", description = "사용자의 작가 권한 요청을 거절합니다.")
    public ResponseEntity<ApiResponse<Void>> reject(@PathVariable Long requestId) {
        adminRoleRequestService.reject(requestId);
        return ResponseEntity.ok(ApiResponse.success("작가 권한 요청을 거절했습니다.", null));
    }
}