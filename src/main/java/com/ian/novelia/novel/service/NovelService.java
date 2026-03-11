package com.ian.novelia.novel.service;

import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.novel.domain.Novel;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.repository.NovelRepository;
import com.ian.novelia.novel.search.NovelSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ian.novelia.global.error.code.ErrorCode.NOVEL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NovelService {

    private final NovelRepository novelRepository;

    public Page<GetNovelResponseDto> getNovels(NovelSearchCondition searchCondition, Pageable pageable) {
        return novelRepository.searchNovels(
                        searchCondition.getSearchTypeOrNull(),
                        searchCondition.getKeywordOrNull(),
                        searchCondition.getCategoryOrNull(),
                        pageable
                )
                .map(GetNovelResponseDto::from);
    }

    public GetNovelResponseDto getNovel(Long novelId) {
        Novel novel = novelRepository.findByIdAndDeletedAtIsNull(novelId)
                .orElseThrow(() -> new CustomException(NOVEL_NOT_FOUND));

        return GetNovelResponseDto.from(novel);
    }
}