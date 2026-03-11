package com.ian.novelia.admin.rolerequest.repository;

import com.ian.novelia.rolerequest.domain.RoleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleRequestRepository extends JpaRepository<RoleRequest, Long> {
}
