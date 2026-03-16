package com.ian.novelia.rolerequest.repository;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.rolerequest.domain.RoleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {

    @Query("""
                select count(r) > 0
                from RoleRequest r
                where r.user = :user
                  and r.status = com.ian.novelia.rolerequest.domain.RoleRequestStatus.PENDING
            """)
    boolean existsPendingByUser(@Param("user") User user);
}
