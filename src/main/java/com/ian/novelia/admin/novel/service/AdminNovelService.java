package com.ian.novelia.admin.novel.service;

import com.ian.novelia.admin.novel.repository.AdminNovelRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.novel.domain.Novel;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.search.NovelSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ian.novelia.global.error.code.ErrorCode.NOVEL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNovelService {

    private final AdminNovelRepository adminNovelRepository;

    public Page<GetNovelResponseDto> getNovels(
            NovelSearchCondition searchCondition, Pageable pageable
    ) {
        return adminNovelRepository.searchNovels(
                        searchCondition.getSearchTypeOrNull(),
                        searchCondition.getKeywordOrNull(),
                        searchCondition.getCategoryOrNull(),
                        pageable
                )
                .map(GetNovelResponseDto::from);
    }

    public GetNovelResponseDto getNovel(Long novelId, CustomUserDetails userDetails) {
        Novel novel = adminNovelRepository.findById(novelId)
                .orElseThrow(() -> new CustomException(NOVEL_NOT_FOUND));

        return GetNovelResponseDto.from(novel);
    }

    @Transactional
    public void deleteNovels(List<Long> novelIds, CustomUserDetails userDetails) {
        for (Long novelId : novelIds) {
            Novel novel = adminNovelRepository.findByIdAndDeletedAtIsNull(novelId)
                    .orElseThrow(() -> new CustomException(NOVEL_NOT_FOUND));
            novel.delete();
        }
    }
}