package com.ian.novelia.purchase.domain;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.episode.domain.Episode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "episode_id"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDateTime purchasedAt;
}
