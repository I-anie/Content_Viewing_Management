package com.ian.novelia.rolerequest.repository;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.rolerequest.domain.RoleRequest;
import com.ian.novelia.rolerequest.domain.RoleRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {

    boolean existsByUserAndStatus(User user, RoleRequestStatus roleRequestStatus);
}
