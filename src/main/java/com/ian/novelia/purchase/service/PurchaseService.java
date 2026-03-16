package com.ian.novelia.purchase.service;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.auth.repository.UserRepository;
import com.ian.novelia.episode.domain.Episode;
import com.ian.novelia.episode.repository.EpisodeRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.purchase.domain.Purchase;
import com.ian.novelia.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ian.novelia.global.error.code.ErrorCode.EPISODE_NOT_FOUND;
import static com.ian.novelia.global.error.code.ErrorCode.PURCHASE_ALREADY_PURCHASED;
import static com.ian.novelia.global.error.code.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void purchaseEpisode(Long novelId, Long episodeId, String username) {
        User user = findUser(username);
        Episode episode = findEpisode(novelId, episodeId);

        validateNotPurchased(user.getId(), episode.getId());

        Purchase purchase = Purchase.builder()
                .user(user)
                .episode(episode)
                .price(episode.getPrice())
                .purchasedAt(LocalDateTime.now())
                .build();

        purchaseRepository.save(purchase);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Episode findEpisode(Long novelId, Long episodeId) {
        return episodeRepository.findByIdAndNovelIdAndDeletedAtIsNull(episodeId, novelId)
                .orElseThrow(() -> new CustomException(EPISODE_NOT_FOUND));
    }

    private void validateNotPurchased(Long userId, Long episodeId) {
        if (isAlreadyPurchased(userId, episodeId)) {
            throw new CustomException(PURCHASE_ALREADY_PURCHASED);
        }
    }

    private boolean isAlreadyPurchased(Long userId, Long episodeId) {
        return purchaseRepository.existsByUserIdAndEpisodeId(userId, episodeId);
    }
}