package com.ian.novelia.rolerequest.domain;

import com.ian.novelia.auth.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.ian.novelia.rolerequest.domain.RoleRequestStatus.PENDING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "role_request")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RoleRequest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RoleRequestStatus status = PENDING;

    @Column(nullable = false)
    private String penName;

    @CreatedDate
    @Column
    private LocalDateTime requestedAt;

    @Column
    private LocalDateTime processedAt;

    public void updateStatus(RoleRequestStatus status) {
        this.status = status;
    }

    public void updateProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
