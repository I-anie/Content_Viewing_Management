package com.ian.novelia.episode.service;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.auth.repository.UserRepository;
import com.ian.novelia.episode.domain.Episode;
import com.ian.novelia.episode.dto.response.GetEpisodeResponseDto;
import com.ian.novelia.episode.dto.response.GetEpisodesResponseDto;
import com.ian.novelia.episode.repository.EpisodeRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.novel.domain.Novel;
import com.ian.novelia.novel.repository.NovelRepository;
import com.ian.novelia.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ian.novelia.global.error.code.ErrorCode.EPISODE_NOT_FOUND;
import static com.ian.novelia.global.error.code.ErrorCode.NOVEL_NOT_FOUND;
import static com.ian.novelia.global.error.code.ErrorCode.PURCHASE_REQUIRED;
import static com.ian.novelia.global.error.code.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EpisodeService {

    private final NovelRepository novelRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public Page<GetEpisodesResponseDto> getEpisodes(Long novelId, Pageable pageable) {
        Novel novel = findNovel(novelId);

        return episodeRepository.findAllByNovelAndDeletedAtIsNull(novel, pageable)
                .map(GetEpisodesResponseDto::from);
    }

    public GetEpisodeResponseDto getEpisode(Long novelId, Long episodeId, String username) {
        Episode episode = findEpisode(novelId, episodeId);
        User user = findUser(username);

        validatePurchased(user.getId(), episode.getId());

        return GetEpisodeResponseDto.from(episode);
    }

    private Novel findNovel(Long novelId) {
        return novelRepository.findByIdAndDeletedAtIsNull(novelId)
                .orElseThrow(() -> new CustomException(NOVEL_NOT_FOUND));
    }

    private Episode findEpisode(Long novelId, Long episodeId) {
        return episodeRepository.findByIdAndNovelIdAndDeletedAtIsNull(episodeId, novelId)
                .orElseThrow(() -> new CustomException(EPISODE_NOT_FOUND));
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private void validatePurchased(Long userId, Long episodeId) {
        if (!purchaseRepository.existsByUserIdAndEpisodeId(userId, episodeId)) {
            throw new CustomException(PURCHASE_REQUIRED);
        }
    }
}