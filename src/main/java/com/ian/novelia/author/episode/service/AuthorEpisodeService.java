package com.ian.novelia.author.episode.service;

import com.ian.novelia.author.episode.dto.request.CreateEpisodeRequestDto;
import com.ian.novelia.author.episode.dto.request.UpdateEpisodeRequestDto;
import com.ian.novelia.author.novel.repository.AuthorNovelRepository;
import com.ian.novelia.episode.domain.Episode;
import com.ian.novelia.episode.dto.response.GetEpisodeResponseDto;
import com.ian.novelia.episode.dto.response.GetEpisodesResponseDto;
import com.ian.novelia.episode.repository.EpisodeRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.novel.domain.Novel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ian.novelia.global.error.code.ErrorCode.EPISODE_NOT_FOUND;
import static com.ian.novelia.global.error.code.ErrorCode.NOVEL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorEpisodeService {

    private final AuthorNovelRepository authorNovelRepository;
    private final EpisodeRepository episodeRepository;

    public Page<GetEpisodesResponseDto> getEpisodes(Long novelId, Pageable pageable, CustomUserDetails userDetails) {
        Novel novel = findAuthorizedNovel(novelId, userDetails.getUsername());

        return episodeRepository.findAllByNovelAndDeletedAtIsNull(novel, pageable)
                .map(GetEpisodesResponseDto::from);
    }

    @Transactional
    public GetEpisodeResponseDto createEpisode(
            Long novelId,
            CreateEpisodeRequestDto request,
            CustomUserDetails userDetails
    ) {
        Novel novel = findAuthorizedNovel(novelId, userDetails.getUsername());

        Episode episode = Episode.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .novel(novel)
                .build();

        return GetEpisodeResponseDto.from(episodeRepository.save(episode));
    }

    public GetEpisodeResponseDto getEpisode(Long novelId, Long episodeId, CustomUserDetails userDetails) {
        Episode episode = findAuthorizedEpisode(novelId, episodeId, userDetails.getUsername());
        return GetEpisodeResponseDto.from(episode);
    }

    @Transactional
    public GetEpisodeResponseDto updateEpisode(
            Long novelId,
            Long episodeId,
            UpdateEpisodeRequestDto request,
            CustomUserDetails userDetails
    ) {
        Episode episode = findAuthorizedEpisode(novelId, episodeId, userDetails.getUsername());
        episode.updateEpisode(request.getTitle(), request.getContent());

        return GetEpisodeResponseDto.from(episode);
    }

    @Transactional
    public void deleteEpisode(Long novelId, Long episodeId, CustomUserDetails userDetails) {
        Episode episode = findAuthorizedEpisode(novelId, episodeId, userDetails.getUsername());
        episode.delete();
    }

    private Novel findAuthorizedNovel(Long novelId, String username) {
        return authorNovelRepository.findByIdAndUserUsernameAndDeletedAtIsNull(novelId, username)
                .orElseThrow(() -> new CustomException(NOVEL_NOT_FOUND));
    }

    private Episode findAuthorizedEpisode(Long novelId, Long episodeId, String username) {
        return episodeRepository.findByIdAndNovelIdAndNovelUserUsernameAndDeletedAtIsNull(
                        episodeId, novelId, username
                )
                .orElseThrow(() -> new CustomException(EPISODE_NOT_FOUND));
    }
}