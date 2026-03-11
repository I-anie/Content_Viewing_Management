package com.ian.novelia.rolerequest.service;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.auth.repository.UserRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.rolerequest.domain.RoleRequest;
import com.ian.novelia.rolerequest.dto.response.AuthorRoleResponseDto;
import com.ian.novelia.rolerequest.repository.RoleRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ian.novelia.auth.domain.Role.AUTHOR;
import static com.ian.novelia.global.error.code.ErrorCode.*;
import static com.ian.novelia.rolerequest.domain.RoleRequestStatus.PENDING;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleRequestService {

    private final RoleRequestRepository roleRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public AuthorRoleResponseDto authorRole(String penName, CustomUserDetails userDetails) {
        User user = findUser(userDetails.getUsername());
        validateAuthorRoleRequest(user);

        RoleRequest roleRequest = RoleRequest.builder()
                .user(user)
                .penName(penName)
                .build();

        RoleRequest savedRoleRequest = roleRequestRepository.save(roleRequest);
        return AuthorRoleResponseDto.from(savedRoleRequest);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private void validateAuthorRoleRequest(User user) {
        if (user.getRoles().contains(AUTHOR)) {
            throw new CustomException(ROLE_REQUEST_ALREADY_AUTHOR);
        }

        if (roleRequestRepository.existsByUserAndStatus(user, PENDING)) {
            throw new CustomException(ROLE_REQUEST_DUPLICATE);
        }
    }
}