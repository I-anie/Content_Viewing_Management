package com.ian.novelia.rolerequest.dto.response;

import com.ian.novelia.rolerequest.domain.RoleRequest;
import com.ian.novelia.rolerequest.domain.RoleRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "작가 권한 요청 응답")
public class AuthorRoleResponseDto {

    @Schema(
            description = "작가 권한 요청 식별자",
            example = "392"
    )
    private Long id;

    @Schema(
            description = "작가 권한 요청 상태",
            allowableValues = {"PENDING", "APPROVED", "REJECTED"},
            example = "PENDING"
    )
    private RoleRequestStatus status;

    public static AuthorRoleResponseDto from(RoleRequest roleRequest) {
        return AuthorRoleResponseDto.builder()
                .id(roleRequest.getId())
                .status(roleRequest.getStatus())
                .build();
    }
}
