package com.ian.novelia.admin.rolerequest.service;

import com.ian.novelia.admin.rolerequest.repository.AdminRoleRequestRepository;
import com.ian.novelia.auth.domain.User;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.rolerequest.domain.RoleRequest;
import com.ian.novelia.rolerequest.domain.RoleRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ian.novelia.auth.domain.Role.AUTHOR;
import static com.ian.novelia.global.error.code.ErrorCode.ROLE_REQUEST_ALREADY_AUTHOR;
import static com.ian.novelia.global.error.code.ErrorCode.ROLE_REQUEST_ALREADY_PROCESSED;
import static com.ian.novelia.global.error.code.ErrorCode.ROLE_REQUEST_NOT_FOUND;
import static com.ian.novelia.rolerequest.domain.RoleRequestStatus.APPROVED;
import static com.ian.novelia.rolerequest.domain.RoleRequestStatus.PENDING;
import static com.ian.novelia.rolerequest.domain.RoleRequestStatus.REJECTED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminRoleRequestService {

    private final AdminRoleRequestRepository adminRoleRequestRepository;

    @Transactional
    public void approve(Long requestId) {
        RoleRequest roleRequest = findRoleRequest(requestId);
        User user = roleRequest.getUser();

        validateProcessable(roleRequest, user);

        roleRequest.updateStatus(APPROVED);
        roleRequest.updateProcessedAt(LocalDateTime.now());
        user.updateAuthorRole(AUTHOR);
        user.updatePenName(roleRequest.getPenName());
    }

    @Transactional
    public void reject(Long requestId) {
        RoleRequest roleRequest = findRoleRequest(requestId);
        User user = roleRequest.getUser();

        validateProcessable(roleRequest, user);

        roleRequest.updateStatus(REJECTED);
        roleRequest.updateProcessedAt(LocalDateTime.now());
    }

    private RoleRequest findRoleRequest(Long requestId) {
        return adminRoleRequestRepository.findById(requestId)
                .orElseThrow(() -> new CustomException(ROLE_REQUEST_NOT_FOUND));
    }

    private void validateProcessable(RoleRequest roleRequest, User user) {
        if (user.getRoles().contains(AUTHOR)) {
            throw new CustomException(ROLE_REQUEST_ALREADY_AUTHOR);
        }

        RoleRequestStatus status = roleRequest.getStatus();
        if (status != PENDING) {
            throw new CustomException(ROLE_REQUEST_ALREADY_PROCESSED);
        }
    }
}