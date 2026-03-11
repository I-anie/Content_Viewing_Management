package com.ian.novelia.author.novel.service;

import com.ian.novelia.auth.domain.User;
import com.ian.novelia.auth.repository.UserRepository;
import com.ian.novelia.author.novel.dto.request.CreateNovelRequestDto;
import com.ian.novelia.author.novel.dto.request.UpdateNovelRequestDto;
import com.ian.novelia.author.novel.dto.response.CreateNovelResponseDto;
import com.ian.novelia.author.novel.dto.response.UpdateNovelResponseDto;
import com.ian.novelia.author.novel.repository.AuthorNovelRepository;
import com.ian.novelia.global.error.exception.CustomException;
import com.ian.novelia.global.security.CustomUserDetails;
import com.ian.novelia.novel.domain.Novel;
import com.ian.novelia.novel.dto.response.GetNovelResponseDto;
import com.ian.novelia.novel.search.NovelSearchCondition;
import com.ian.novelia.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ian.novelia.auth.domain.Role.AUTHOR;
import static com.ian.novelia.global.error.code.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorNovelService {

    private final AuthorNovelRepository novelRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    public Page<GetNovelResponseDto> getNovels(
            NovelSearchCondition searchCondition, Pageable pageable, CustomUserDetails userDetails
    ) {
        User user = findUserByUsernameOrElseThrow(userDetails.getUsername());

        return novelRepository.searchNovels(
                        user,
                        searchCondition.getSearchTypeOrNull(),
                        searchCondition.getKeywordOrNull(),
                        searchCondition.getCategoryOrNull(),
                        pageable
                )
                .map(GetNovelResponseDto::from);
    }

    @Transactional
    public CreateNovelResponseDto createNovel(CreateNovelRequestDto request, String username) {
        User user = findUserByUsernameOrElseThrow(username);
        validateAuthorRole(user);

        try {
            String thumbnailUrl = s3Service.uploadFile(request.getThumbnail());

            return CreateNovelResponseDto.from(
                    novelRepository.save(
                            Novel.builder()
                                    .user(user)
                                    .thumbnailUrl(thumbnailUrl)
                                    .title(request.getTitle())
                                    .description(request.getDescription())
                                    .category(request.getCategory())
                                    .build()
                    ));
        } catch (IOException e) {
            log.error("[AuthorNovelService] createNovel - S3 이미지 업로드 실패: ", e);
            throw new CustomException(S3_UPLOAD_FAILED);
        }
    }

    public GetNovelResponseDto getNovel(Long novelId, String username) {
        Novel novel = findAuthorizedNovel(novelId, username);

        return GetNovelResponseDto.from(novel);
    }

    @Transactional
    public UpdateNovelResponseDto updateNovel(Long novelId, UpdateNovelRequestDto request, String username) {
        log.debug("[AuthorNovelService] updateNovel - request = {}", request);
        Novel novel = findAuthorizedNovel(novelId, username);

        MultipartFile thumbnail = request.getThumbnail();
        String thumbnailUrl = null;

        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                thumbnailUrl = s3Service.uploadFile(thumbnail);
            } catch (IOException e) {
                log.error("[AuthorNovelService] updateNovel - S3 이미지 업로드 실패: ", e);
                throw new CustomException(S3_UPLOAD_FAILED);
            }
        }

        novel.updateNovel(thumbnailUrl, request.getTitle(), request.getDescription(), request.getCategory());

        return UpdateNovelResponseDto.from(novel);
    }

    @Transactional
    public void deleteNovel(Long novelId, String username) {
        Novel novel = findAuthorizedNovel(novelId, username);
        novel.delete();
    }

    private User findUserByUsernameOrElseThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("[AuthorNovelService] - 회원을 찾을 수 없음: username = {}", username);
                    return new CustomException(USER_NOT_FOUND);
                });
    }

    private Novel findAuthorizedNovel(Long novelId, String username) {
        return novelRepository.findByIdAndUserUsernameAndDeletedAtIsNull(novelId, username)
                .orElseThrow(() -> {
                    log.error("[AuthorNovelService] - 소설을 찾을 수 없음: novelId = {}", novelId);
                    return new CustomException(NOVEL_NOT_FOUND);
                });
    }

    private void validateAuthorRole(User user) {
        if (!isAuthor(user)) {
            log.error("[AuthorNovelService] 작가 권한이 없는 사용자: username = {}", user.getUsername());
            throw new CustomException(AUTHOR_ROLE_REQUIRED);
        }
    }

    private boolean isAuthor(User user) {
        return user.getRoles().contains(AUTHOR);
    }
}
