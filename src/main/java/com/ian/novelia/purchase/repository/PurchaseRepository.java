package com.ian.novelia.purchase.repository;

import com.ian.novelia.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    boolean existsByUserIdAndEpisodeId(Long userId, Long episodeId);
}
