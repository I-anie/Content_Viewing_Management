package com.ian.novelia.episode.repository;

import com.ian.novelia.episode.domain.Episode;
import com.ian.novelia.novel.domain.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Page<Episode> findAllByNovelAndDeletedAtIsNull(Novel novel, Pageable pageable);

    Optional<Episode> findByIdAndNovelIdAndDeletedAtIsNull(Long episodeId, Long novelId);

    Optional<Episode> findByIdAndNovelIdAndNovelUserUsernameAndDeletedAtIsNull(
            Long episodeId,
            Long novelId,
            String username
    );
}